package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GpsDataRepository extends JpaRepository<GpsData, Integer> {

    GpsData findByGpsDataId(int id);
    List<GpsData> findByAnimal(Animal animal);
    List<GpsData> findByAnimalAndCreatedTimeAfter(Animal animal, LocalDateTime dateTime);
    GpsData findFirstByAnimalOrderByGpsDataIdDesc(Animal animal);
    List<GpsData> findFirst10ByAnimalOrderByGpsDataIdDesc(Animal animal);
    void deleteByCreatedTimeBefore(LocalDateTime dateTime);
    void deleteByAnimalAndCreatedTimeBefore(Animal animal, LocalDateTime dateTime);
    List<GpsData> findByAnimalAndCreatedTimeBetween(Animal animal, LocalDateTime start, LocalDateTime end);

}