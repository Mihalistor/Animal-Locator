package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.SafeZone;
import com.animal.locator.animalLocator.repository.SafeZoneRepository;
import com.animal.locator.animalLocator.services.SafeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("safeZoneService")
public class SafeZoneServiceImpl implements SafeZoneService {

    @Autowired
    SafeZoneRepository safeZoneRepository;

    @Override
    public SafeZone save(SafeZone safeZone) {
        return safeZoneRepository.save(safeZone);
    }

    @Override
    public SafeZone update(SafeZone safeZone) {
        return safeZoneRepository.save(safeZone);
    }

    @Override
    public List<SafeZone> findAll() {
        return safeZoneRepository.findAll();
    }

    @Override
    public List<SafeZone> findByAnimal(Animal animal) {
        return safeZoneRepository.findByAnimal(animal);
    }

    @Override
    public void deleteByAnimal(Animal animal) {
        safeZoneRepository.deleteByAnimal(animal);
    }
}