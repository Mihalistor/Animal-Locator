package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.Treatment;
import com.animal.locator.animalLocator.models.TreatmentType;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {

    Treatment findByTreatmentId(int id);
    List<Treatment> findByAnimalOrderByTreatmentDateDesc(Animal animal);
    List<Treatment> findByTreatmentType(TreatmentType treatmentType);
    List<Treatment> findByTreatmentTypeAndAnimal(TreatmentType treatmentType, Animal animal);
    List<Treatment> findByAnimalAndTreatmentAlertTimeAfter(Animal animal, LocalDateTime date);
    List<Treatment> findByTreatmentAlertTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Treatment> findByAnimalAndTreatmentDateAfter(Animal animal, LocalDateTime date);
}