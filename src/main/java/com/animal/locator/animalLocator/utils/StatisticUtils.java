package com.animal.locator.animalLocator.utils;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.AnimalStatistic;
import com.animal.locator.animalLocator.models.GpsData;
import com.animal.locator.animalLocator.services.AnimalService;
import com.animal.locator.animalLocator.services.AnimalStatisticService;
import com.animal.locator.animalLocator.services.GpsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class StatisticUtils {

    @Autowired
    GpsDataService gpsDataService;

    @Autowired
    AnimalService animalService;

    @Autowired
    AnimalStatisticService animalStatisticService;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void checkAnimalStatistic() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now();

        for (Animal animal : animalService.findAll()) {
            AnimalStatistic statistic = animalStatisticService.findByAnimalAndCreatedTime(animal, LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)));
            if (ObjectUtils.isEmpty(statistic)) {
                createStatisticForAnimal(animal);
            }
            statistic = animalStatisticService.findByAnimalAndCreatedTime(animal, LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)));
            List<GpsData> gpsDataList = gpsDataService.findByAnimalAndCreatedTimeBetween(animal, start, end);
            for (int i = 0; i<gpsDataList.size()-1; i++) {
                float latitude = gpsDataList.get(i).getLatitude().floatValue();
                float longitude = gpsDataList.get(i).getLongitude().floatValue();
                float latitudeNext = gpsDataList.get(i+1).getLatitude().floatValue();
                float longitudeNext = gpsDataList.get(i+1).getLongitude().floatValue();
                Float dist = (float) Math.sqrt(Math.pow(latitude - latitudeNext, 2) + Math.pow(longitude - longitudeNext, 2) );
                if (dist.equals(0)) {
                    statistic.setSleeping(statistic.getSleeping()+5);
                } else {
                    statistic.setRunning(statistic.getRunning()+5);
                }
                animalStatisticService.update(statistic);
            }
        }
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void createStatistic() {
        for (Animal animal : animalService.findAll()) {
            AnimalStatistic animalStatistic =  new AnimalStatistic();
            animalStatistic.setRunning(0);
            animalStatistic.setSleeping(0);
            animalStatistic.setAnimal(animal);
            animalStatistic.setCreatedTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0,0,0)));
            animalStatisticService.save(animalStatistic);
        }
    }

    public void createStatisticForAnimal(Animal animal) {
            AnimalStatistic animalStatistic =  new AnimalStatistic();
            animalStatistic.setRunning(0);
            animalStatistic.setSleeping(0);
            animalStatistic.setAnimal(animal);
            animalStatistic.setCreatedTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)));
            animalStatisticService.save(animalStatistic);
    }

}
