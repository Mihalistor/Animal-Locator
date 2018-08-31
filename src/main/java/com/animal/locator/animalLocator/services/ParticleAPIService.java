package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.GpsLocator;

public interface ParticleAPIService {

    void sendMessageToPhoton(GpsLocator gpsLocator, String value, String function);
    String getLatitude(GpsLocator gpsLocator);
    String getLongitude(GpsLocator gpsLocator);
}