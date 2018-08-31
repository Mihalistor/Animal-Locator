package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GpsLocatorRepository extends JpaRepository<GpsLocator, Integer> {

    GpsLocator findByGpsLocatorId(int id);

}