package com.animal.locator.animalLocator.utils;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsData;
import com.animal.locator.animalLocator.models.SafeZone;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.awt.geom.Path2D;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GpaDataUtils {

    @Autowired
    AnimalService animalService;

    @Autowired
    GpsDataService gpsDataService;

    @Autowired
    SafeZoneService safeZoneService;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    SMSSendService smsSendService;

    @Autowired
    UserService userService;

    @Autowired
    ParticleAPIService particleAPIService;

    @Scheduled(cron = "*/5 * * * * *")
    public void saveGpsDataToDatabase() {
        List<Animal> animals = animalService.findAll();
        for (Animal animal : animals) {
            if(animal.getGpsLocator().getStatus()) {
                try {
                    Double latitude = Double.parseDouble(particleAPIService.getLatitude(animal.getGpsLocator()));
                    Double longitude = Double.parseDouble(particleAPIService.getLongitude(animal.getGpsLocator()));
                    GpsData gpsData = new GpsData();
                    gpsData.setLatitude(latitude);
                    gpsData.setLongitude(longitude);
                    gpsData.setCreatedTime(LocalDateTime.now());
                    gpsData.setAnimal(animal);
                    gpsDataService.save(gpsData);
                    if (!checkIfAnimalIsInsideZone(animal)) {
                        sendNotificationAfterDelay(animal, animal.getUser());
                    }
                }catch (Exception e) {
                    System.out.println("Error with saving gps data in database");
                }
            }
        }
    }

    public boolean checkIfAnimalIsInsideZone(Animal animal) {
        List<SafeZone> safeZoneElements = safeZoneService.findByAnimal(animal);
        if(safeZoneElements.size() > 0) {
            Path2D path = new Path2D.Double();
            path.moveTo(safeZoneElements.get(0).getLatitude(), safeZoneElements.get(0).getLongitude());
            for (int i = 1; i<safeZoneElements.size(); i++) {
                path.lineTo(safeZoneElements.get(i).getLatitude(), safeZoneElements.get(i).getLongitude());
            }
            path.closePath();
            GpsData lastGpsData = gpsDataService.findFirstByAnimalOrderByGpsDataIdDesc(animal);
            return path.contains(lastGpsData.getLatitude(), lastGpsData.getLongitude());
        }
        return false;
    }

    public void sendNotificationAfterDelay(Animal animal, User user) throws IOException {
        if (LocalDateTime.now().isAfter(animal.getLastNotification().plusMinutes(user.getDelay()))) {
            String subject = "Animal Locator";
            String body = "Animal " + animal.getAnimalName() + " is outside safe zone!";
            animal.setLastNotification(LocalDateTime.now());
            animalService.update(animal);
            particleAPIService.sendMessageToPhoton(animal.getGpsLocator(),  "OUTSIDE", "location");
            if (user.getEmailNotification()) {
                emailSendService.sendEmail(subject, body, user);
            }
            if (user.getSMSNotification()) {
                smsSendService.sendSMS(body, user);
            }
            if(user.getMobileNotification()) {
                particleAPIService.sendMessageToPhoton(animal.getGpsLocator(),  animal.getAnimalName(), "notific");
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * *")
    public void deleteGpsDataToDatabase() {
        List<Animal> animals = animalService.findAll();
        for (Animal animal : animals) {
            if(animal.getUser().getDeleteData()) {
                gpsDataService.deleteByAnimalAndCreatedTimeBefore(animal, LocalDateTime.now().minusMonths(1));
            }
        }
    }
}
