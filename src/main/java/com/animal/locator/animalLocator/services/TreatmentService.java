package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.Treatment;
import com.animal.locator.animalLocator.models.TreatmentType;

import java.time.LocalDateTime;
import java.util.List;

public interface TreatmentService {

    Treatment save(Treatment treatment);
    Treatment update(Treatment treatment);
    List<Treatment> findAll();
    Treatment findByTreatmentId(int id);
    List<Treatment> findByAnimalOrderByTreatmentDateDesc(Animal animal);
    List<Treatment> findByTreatmentType(TreatmentType treatmentType);
    List<Treatment> findByTreatmentTypeAndAnimal(TreatmentType treatmentType, Animal animal);
    List<Treatment> findByAnimalAndTreatmentAlertTimeAfter(Animal animal, LocalDateTime date);
    List<Treatment> findByTreatmentAlertTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Treatment> findByAnimalAndTreatmentDateAfter(Animal animal, LocalDateTime date);

}