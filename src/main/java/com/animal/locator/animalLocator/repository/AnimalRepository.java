package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    Animal findByAnimalId(int id);
    List<Animal> findByUser(User user);
    List<Animal>  findByAnimalName(String name);
    Animal findByAnimalTransponderCode(String transponderCode);
    Animal findByGpsLocator(GpsLocator gpsLocator);

}