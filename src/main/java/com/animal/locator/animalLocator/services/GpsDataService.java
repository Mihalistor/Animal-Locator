package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsData;

import java.time.LocalDateTime;
import java.util.List;

public interface GpsDataService {

    GpsData save(GpsData gpsData);
    GpsData update(GpsData gpsData);
    List<GpsData> findAll();
    GpsData findByGpsDataId(int id);
    List<GpsData> findByAnimal(Animal animal);
    List<GpsData> findByAnimalAndCreatedTimeAfter(Animal animal, LocalDateTime dateTime);
    GpsData findFirstByAnimalOrderByGpsDataIdDesc(Animal animal);
    List<GpsData> findFirst10ByAnimalOrderByGpsDataIdDesc(Animal animal);
    void deleteByCreatedTimeBefore(LocalDateTime dateTime);
    void deleteByAnimalAndCreatedTimeBefore(Animal animal, LocalDateTime dateTime);
    List<GpsData> findByAnimalAndCreatedTimeBetween(Animal animal, LocalDateTime start, LocalDateTime end);

}