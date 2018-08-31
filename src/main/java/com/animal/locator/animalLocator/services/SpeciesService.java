package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Species;

import java.util.List;

public interface SpeciesService {

    Species save(Species species);
    List<Species> findAll();
    Species findBySpeciesId(int id);
    Species findByName(String name);
    List<Species> findAllByOrderByNameAsc();

}