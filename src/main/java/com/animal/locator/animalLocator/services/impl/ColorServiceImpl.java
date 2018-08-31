package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.Color;
import com.animal.locator.animalLocator.repository.ColorRepository;
import com.animal.locator.animalLocator.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("colorService")
public class ColorServiceImpl implements ColorService {

    @Autowired
    ColorRepository colorRepository;

    @Override
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color findByColorId(int id) {
        return colorRepository.findByColorId(id);
    }

    @Override
    public Color findByName(String name) {
        return colorRepository.findByName(name);
    }

}