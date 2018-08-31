package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Breed;
import com.animal.locator.animalLocator.repository.BreedRepository;
import com.animal.locator.animalLocator.services.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("breedService")
public class BreedServiceImpl implements BreedService {

    @Autowired
    BreedRepository breedRepository;

    @Override
    public Breed save(Breed breed) {
        return breedRepository.save(breed);
    }

    @Override
    public List<Breed> findAll() {
        return breedRepository.findAll();
    }

    @Override
    public Breed findByBreedId(int id) {
        return breedRepository.findByBreedId(id);
    }

    @Override
    public Breed findByName(String name) {
        return breedRepository.findByName(name);
    }

}