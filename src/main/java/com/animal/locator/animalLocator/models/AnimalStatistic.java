package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "animal_activity")
public class AnimalStatistic {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "statistic_id")
    private int statisticId;

    @Column(name = "sleeping")
    private long sleeping = 0;

    @Column(name = "running")
    private long running = 0;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @ManyToOne
    private Animal animal;


    public AnimalStatistic() {
    }

    public int getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
    }

    public long getSleeping() {
        return sleeping;
    }

    public String getSleepingHours() {
        return String.format("%.02f", (float) sleeping/60/60) + " h - " + String.format("%.02f", ((float) sleeping*100)/(running+sleeping)) + "%";
    }

    public void setSleeping(long sleeping) {
        this.sleeping = sleeping;
    }

    public long getRunning() {
        return running;
    }

    public String getRunningHours() {
        return String.format("%.02f", (float) running/60/60) + " h - " + String.format("%.02f", ((float) running*100)/(running+sleeping)) + "%";
    }

    public void setRunning(long running) {
        this.running = running;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getCreatedTimeString() { return createdTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")); }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

}