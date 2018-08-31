package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.User;

public interface EmailSendService {

    void sendEmail(String subject, String body, User user);

}
