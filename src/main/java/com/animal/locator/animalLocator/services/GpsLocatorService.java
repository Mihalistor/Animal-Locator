package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;

import java.util.List;

public interface GpsLocatorService {

    GpsLocator save(GpsLocator gpsLocator);
    GpsLocator update(GpsLocator gpsLocator);
    List<GpsLocator> findAll();
    GpsLocator findByGpsLocatorId(int id);

}