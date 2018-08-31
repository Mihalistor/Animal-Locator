package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.SafeZone;

import java.util.List;

public interface SafeZoneService {

    SafeZone save(SafeZone safeZone);
    SafeZone update(SafeZone safeZone);
    List<SafeZone> findAll();
    List<SafeZone> findByAnimal(Animal animal);
    void deleteByAnimal(Animal animal);

}