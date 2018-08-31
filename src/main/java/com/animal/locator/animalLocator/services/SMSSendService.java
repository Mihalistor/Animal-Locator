package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.User;

public interface SMSSendService {

    void sendSMS(String body, User user);

}
