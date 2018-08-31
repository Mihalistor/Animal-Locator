package com.animal.locator.animalLocator.models;

public enum AnimalSex {

    MALE("Male"),
    FEMALE("Female");

    private String animalSex;

    AnimalSex(String animalSex) {
        this.animalSex = animalSex;
    }

    public String getAnimalSex() {
        return animalSex;
    }

}