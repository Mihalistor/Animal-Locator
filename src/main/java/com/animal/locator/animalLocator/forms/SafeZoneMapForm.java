package com.animal.locator.animalLocator.forms;

import com.animal.locator.animalLocator.models.Animal;

import java.util.ArrayList;
import java.util.List;

public class SafeZoneMapForm {

    String safeZoneElements;

    Animal animalZone;

    List<Animal> animalsZone = new ArrayList<>();

    Boolean anotherAnimal;

    String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getSafeZoneElements() {
        return safeZoneElements;
    }

    public void setSafeZoneElements(String safeZoneElements) {
        this.safeZoneElements = safeZoneElements;
    }

    public Animal getAnimalZone() {
        return animalZone;
    }

    public void setAnimalZone(Animal animalZone) {
        this.animalZone = animalZone;
    }

    public List<Animal> getAnimalsZone() {
        return animalsZone;
    }

    public void setAnimalsZone(List<Animal> animalsZone) {
        this.animalsZone = animalsZone;
    }

    public Boolean getAnotherAnimal() {
        return anotherAnimal;
    }

    public void setAnotherAnimal(Boolean anotherAnimal) {
        this.anotherAnimal = anotherAnimal;
    }

}