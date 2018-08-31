package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.repository.AnimalRepository;
import com.animal.locator.animalLocator.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("animalService")
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    AnimalRepository animalRepository;

    @Override
    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Animal update(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public Animal findByAnimalId(int id) {
        return animalRepository.findByAnimalId(id);
    }

    @Override
    public List<Animal> findByUser(User user) {
        return animalRepository.findByUser(user);
    }

    @Override
    public List<Animal> findByAnimalName(String name) {
        return animalRepository.findByAnimalName(name);
    }

    @Override
    public Animal findByAnimalTransponderCode(String transponderCode) {
        return animalRepository.findByAnimalTransponderCode(transponderCode);
    }

    @Override
    public Animal findByGpsLocator(GpsLocator gpsLocator) {
        return animalRepository.findByGpsLocator(gpsLocator);
    }

}