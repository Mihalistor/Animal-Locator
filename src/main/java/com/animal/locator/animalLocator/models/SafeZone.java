package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "safe_zone")
public class SafeZone {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "safe_zone_id")
    private int safeZoneId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    Animal animal;

    public SafeZone() {
    }

    public int getSafeZoneId() {
        return safeZoneId;
    }

    public void setSafeZoneId(int safeZoneId) {
        this.safeZoneId = safeZoneId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

}