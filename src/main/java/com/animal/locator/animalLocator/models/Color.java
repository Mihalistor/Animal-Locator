package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "color")
public class Color {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "color_id")
    private int colorId;

    @Column(name = "name")
    private String name;

    public Color() {
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}