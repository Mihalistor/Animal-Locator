package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;

import java.util.List;

public interface AnimalService {

    Animal save(Animal animal);
    Animal update(Animal animal);
    List<Animal> findAll();
    Animal findByAnimalId(int id);
    List<Animal> findByUser(User user);
    List<Animal>  findByAnimalName(String name);
    Animal findByAnimalTransponderCode(String transponderCode);
    Animal findByGpsLocator(GpsLocator gpsLocator);

}