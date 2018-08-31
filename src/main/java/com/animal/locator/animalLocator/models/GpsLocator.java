package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "gps_locator")
public class GpsLocator {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "gps_locator_id")
    private int gpsLocatorId;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean status = true;

    @Column(name = "led", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean led = false;

    @Column(name = "deviceId")
    private String deviceId;

    @Column(name = "access_token")
    private String accessToken;

    public GpsLocator() {
    }

    public int getGpsLocatorId() {
        return gpsLocatorId;
    }

    public void setGpsLocatorId(int gpsLocatorId) {
        this.gpsLocatorId = gpsLocatorId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getCreatedTimeString() { return createdTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")); }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getLed() {
        return led;
    }

    public void setLed(Boolean led) {
        this.led = led;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}