package com.animal.locator.animalLocator.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "species")
public class Species {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "species_id")
    private int speciesId;

    @Column(name = "name")
    private String name;

    public Species() {
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}