package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "gps_data")
public class GpsData {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "gps_data_id")
    private int gpsDataId;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    private Animal animal;

    public GpsData() {
    }

    public int getGpsDataId() {
        return gpsDataId;
    }

    public void setGpsDataId(int gpsDataId) {
        this.gpsDataId = gpsDataId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getCreatedTimeString() { return createdTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")); }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
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