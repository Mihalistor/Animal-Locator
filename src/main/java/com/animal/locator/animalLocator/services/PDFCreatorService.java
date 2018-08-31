package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.AnimalStatistic;
import com.animal.locator.animalLocator.models.Treatment;

import java.util.List;

public interface PDFCreatorService {

    String createStatisticReport(List<AnimalStatistic> statistics);
    String createTreatmentReport(List<Treatment> treatments);

}