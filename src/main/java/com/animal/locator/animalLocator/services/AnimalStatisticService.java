package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.AnimalStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface AnimalStatisticService {

    AnimalStatistic save(AnimalStatistic animalStatistic);
    AnimalStatistic update(AnimalStatistic animalStatistic);
    AnimalStatistic findByAnimalAndCreatedTime(Animal animal, LocalDateTime date);
    List<AnimalStatistic> findByAnimalAndCreatedTimeAfterOrderByCreatedTimeAsc(Animal animal, LocalDateTime dateTime);

}