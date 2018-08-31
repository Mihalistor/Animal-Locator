package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Species;
import com.animal.locator.animalLocator.repository.SpeciesRepository;
import com.animal.locator.animalLocator.services.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("speciesService")
public class SpeciesServiceImpl implements SpeciesService {

    @Autowired
    SpeciesRepository speciesRepository;

    @Override
    public Species save(Species species) {
        return speciesRepository.save(species);
    }

    @Override
    public List<Species> findAll() {
        return speciesRepository.findAll();
    }

    @Override
    public Species findBySpeciesId(int id) {
        return speciesRepository.findBySpeciesId(id);
    }

    @Override
    public Species findByName(String name) {
        return speciesRepository.findByName(name);
    }

    @Override
    public List<Species> findAllByOrderByNameAsc() {
        return speciesRepository.findAllByOrderByNameAsc();
    }

}