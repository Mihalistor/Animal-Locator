package com.animal.locator.animalLocator.forms;

import com.animal.locator.animalLocator.models.AnimalSex;

public class EditAnimalForm {

    String name;

    String birthday;

    String species;

    String breed;

    String color;

    AnimalSex sex;

    String transponderCode;

    String transponderLocation;

    String passport;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public AnimalSex getSex() {
        return sex;
    }

    public void setSex(AnimalSex sex) {
        this.sex = sex;
    }

    public String getTransponderCode() {
        return transponderCode;
    }

    public void setTransponderCode(String transponderCode) {
        this.transponderCode = transponderCode;
    }

    public String getTransponderLocation() {
        return transponderLocation;
    }

    public void setTransponderLocation(String transponderLocation) {
        this.transponderLocation = transponderLocation;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

}