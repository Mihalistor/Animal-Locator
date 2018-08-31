package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Integer> {

    Breed findByBreedId(int id);
    Breed findByName(String name);

}