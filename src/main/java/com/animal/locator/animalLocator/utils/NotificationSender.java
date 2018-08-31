package com.animal.locator.animalLocator.utils;

import java.time.LocalDateTime;
import java.util.List;

import com.animal.locator.animalLocator.models.GpsData;
import com.animal.locator.animalLocator.models.Treatment;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender
{
    @Autowired
    TreatmentService treatmentService;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    SMSSendService smsSendService;

    @Autowired
    UserService userService;

    @Autowired
    GpsDataService gpsDataService;

    private static final Logger log = LoggerFactory.getLogger(NotificationSender.class);

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void sendTreatmentNotification() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        List<Treatment> allTreatments = treatmentService.findByTreatmentAlertTimeBetween(start,end);
        for (Treatment treatment : allTreatments){
            if(treatment.getTreatmentNotification()){
                String subject = "Treatment - " + treatment.getTreatmentType() + " for " + treatment.getAnimal().getAnimalName();
                String body = "Treatment date: " + treatment.getTreatmentDateString() + "\nTreatment description: " + treatment.getTreatmentDescription() + "\nAlert time: " + treatment.getTreatmentAlertTimeString();
                User user = userService.findByUserId(treatment.getAnimal().getUser().getUserId());
                if (user.getEmailNotification()) {
                    emailSendService.sendEmail(subject, body, user);
                }
                if (user.getSMSNotification()) {
                    smsSendService.sendSMS(body, user);
                }
            }
        }
    }



}
