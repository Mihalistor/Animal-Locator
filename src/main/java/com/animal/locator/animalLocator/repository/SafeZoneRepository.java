package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.SafeZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SafeZoneRepository extends JpaRepository<SafeZone, Integer> {

    List<SafeZone> findByAnimal(Animal animal);
    void deleteByAnimal(Animal animal);

}