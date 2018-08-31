package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.Treatment;
import com.animal.locator.animalLocator.models.TreatmentType;
import com.animal.locator.animalLocator.repository.TreatmentRepository;
import com.animal.locator.animalLocator.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("treatmentService")
public class TreatmentServiceImpl implements TreatmentService {

    @Autowired
    TreatmentRepository treatmentRepository;

    @Override
    public Treatment save(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    @Override
    public Treatment update(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    @Override
    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

    @Override
    public Treatment findByTreatmentId(int id) {
        return treatmentRepository.findByTreatmentId(id);
    }

    @Override
    public List<Treatment> findByAnimalOrderByTreatmentDateDesc(Animal animal) {
        return treatmentRepository.findByAnimalOrderByTreatmentDateDesc(animal);
    }

    @Override
    public List<Treatment> findByTreatmentType(TreatmentType treatmentType) {
        return treatmentRepository.findByTreatmentType(treatmentType);
    }

    @Override
    public List<Treatment> findByTreatmentTypeAndAnimal(TreatmentType treatmentType, Animal animal) {
        return treatmentRepository.findByTreatmentTypeAndAnimal(treatmentType, animal);
    }

    @Override
    public List<Treatment> findByAnimalAndTreatmentAlertTimeAfter(Animal animal, LocalDateTime date) {
        return treatmentRepository.findByAnimalAndTreatmentAlertTimeAfter(animal, date);
    }

    @Override
    public List<Treatment> findByTreatmentAlertTimeBetween(LocalDateTime start, LocalDateTime end) {
        return treatmentRepository.findByTreatmentAlertTimeBetween(start, end);
    }

    @Override
    public List<Treatment> findByAnimalAndTreatmentDateAfter(Animal animal, LocalDateTime date) {
        return treatmentRepository.findByAnimalAndTreatmentDateAfter(animal, date);
    }

}