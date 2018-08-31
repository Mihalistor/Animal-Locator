package com.animal.locator.animalLocator.services;

import com.animal.locator.animalLocator.models.Color;

import java.util.List;

public interface ColorService {

    Color save(Color color);
    List<Color> findAll();
    Color findByColorId(int id);
    Color findByName(String name);

}