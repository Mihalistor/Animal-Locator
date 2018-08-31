package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Breed;
import com.animal.locator.animalLocator.models.Species;

import java.util.List;

public interface BreedService {

    Breed save(Breed breed);
    List<Breed> findAll();
    Breed findByBreedId(int id);
    Breed findByName(String name);

}