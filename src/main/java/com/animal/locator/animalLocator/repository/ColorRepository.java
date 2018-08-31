package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {

    Color findByColorId(int id);
    Color findByName(String name);

}