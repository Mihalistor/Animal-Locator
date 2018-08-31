package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Animal;
import com.animal.locator.animalLocator.models.AnimalStatistic;
import com.animal.locator.animalLocator.repository.AnimalStatisticRepository;
import com.animal.locator.animalLocator.services.AnimalStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("animalStatisticService")
public class AnimalStatisticServiceImpl implements AnimalStatisticService {

    @Autowired
    AnimalStatisticRepository animalStatisticRepository;

    @Override
    public AnimalStatistic save(AnimalStatistic animalStatistic) {
        return animalStatisticRepository.save(animalStatistic);
    }

    @Override
    public AnimalStatistic update(AnimalStatistic animalStatistic) {
        return animalStatisticRepository.save(animalStatistic);
    }

    @Override
    public AnimalStatistic findByAnimalAndCreatedTime(Animal animal, LocalDateTime date) {
        return animalStatisticRepository.findByAnimalAndCreatedTime(animal, date);
    }

    @Override
    public List<AnimalStatistic> findByAnimalAndCreatedTimeAfterOrderByCreatedTimeAsc(Animal animal, LocalDateTime dateTime) {
        return animalStatisticRepository.findByAnimalAndCreatedTimeAfterOrderByCreatedTimeAsc(animal, dateTime);
    }
}
