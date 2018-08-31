package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.repository.GpsLocatorRepository;
import com.animal.locator.animalLocator.services.GpsLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gpsLocatorService")
public class GpsLocatorServiceImpl implements GpsLocatorService {

    @Autowired
    GpsLocatorRepository gpsLocatorRepository;

    @Override
    public GpsLocator save(GpsLocator gpsLocator) {
        return gpsLocatorRepository.save(gpsLocator);
    }

    @Override
    public GpsLocator update(GpsLocator gpsLocator) {
        return gpsLocatorRepository.save(gpsLocator);
    }

    @Override
    public List<GpsLocator> findAll() {
        return gpsLocatorRepository.findAll();
    }

    @Override
    public GpsLocator findByGpsLocatorId(int id) {
        return gpsLocatorRepository.findByGpsLocatorId(id);
    }

}