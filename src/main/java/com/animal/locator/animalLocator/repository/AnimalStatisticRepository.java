package com.animal.locator.animalLocator.repository;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.AnimalStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AnimalStatisticRepository extends JpaRepository<AnimalStatistic, Integer> {

    AnimalStatistic findByAnimalAndCreatedTime(Animal animal, LocalDateTime date);
    List<AnimalStatistic> findByAnimalAndCreatedTimeAfterOrderByCreatedTimeAsc(Animal animal, LocalDateTime dateTime);

}