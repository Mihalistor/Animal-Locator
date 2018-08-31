package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.GpsData;
import com.animal.locator.animalLocator.repository.GpsDataRepository;
import com.animal.locator.animalLocator.services.GpsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("gpsDataService")
public class GpsDataServiceImpl implements GpsDataService {

    @Autowired
    GpsDataRepository gpsDataRepository;

    @Override
    public GpsData save(GpsData gpsData) {
        return gpsDataRepository.save(gpsData);
    }

    @Override
    public GpsData update(GpsData gpsData) {
        return gpsDataRepository.save(gpsData);
    }

    @Override
    public List<GpsData> findAll() {
        return gpsDataRepository.findAll();
    }

    @Override
    public GpsData findByGpsDataId(int id) {
        return gpsDataRepository.findByGpsDataId(id);
    }

    @Override
    public List<GpsData> findByAnimal(Animal animal) {
        return gpsDataRepository.findByAnimal(animal);
    }

    @Override
    public List<GpsData> findByAnimalAndCreatedTimeAfter(Animal animal, LocalDateTime dateTime) {
        return gpsDataRepository.findByAnimalAndCreatedTimeAfter(animal, dateTime);
    }

    @Override
    public void deleteByCreatedTimeBefore(LocalDateTime dateTime) {
        gpsDataRepository.deleteByCreatedTimeBefore(dateTime);
    }

    @Override
    public void deleteByAnimalAndCreatedTimeBefore(Animal animal, LocalDateTime dateTime) {
        gpsDataRepository.deleteByAnimalAndCreatedTimeBefore(animal, dateTime);
    }

    @Override
    public GpsData findFirstByAnimalOrderByGpsDataIdDesc(Animal animal) {
        return gpsDataRepository.findFirstByAnimalOrderByGpsDataIdDesc(animal);
    }

    @Override
    public List<GpsData> findFirst10ByAnimalOrderByGpsDataIdDesc(Animal animal) {
        return gpsDataRepository.findFirst10ByAnimalOrderByGpsDataIdDesc(animal);
    }

    @Override
    public List<GpsData> findByAnimalAndCreatedTimeBetween(Animal animal, LocalDateTime start, LocalDateTime end) {
        return gpsDataRepository.findByAnimalAndCreatedTimeBetween(animal, start, end);
    }
}