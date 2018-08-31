package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "breed")
public class Breed {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "breed_id")
    private int breedId;

    @Column(name = "name")
    private String name;

    public Breed() {
    }

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}