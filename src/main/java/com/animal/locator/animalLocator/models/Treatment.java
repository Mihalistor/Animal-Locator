package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "treatment")
public class Treatment {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "treatment_id")
    private int treatmentId;

    @Column(name = "date")
    private LocalDateTime treatmentDate;

    @Column(name = "type")
    @Enumerated
    private TreatmentType treatmentType;

    @Column(name = "description")
    private String treatmentDescription;

    @Column(name = "notification", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean treatmentNotification = false;

    @Column(name = "alert_time")
    private LocalDateTime treatmentAlertTime;

    @ManyToOne
    private Animal animal;

    public Treatment() {
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public LocalDateTime getTreatmentDate() {
        return treatmentDate;
    }

    public String getTreatmentDateString() { return treatmentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")); }

    public LocalDateTime getTreatmentDateFromString(String stringTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        final LocalDateTime localDateTime = LocalDateTime.parse(stringTime, formatter);
        return localDateTime;
    }

    public void setTreatmentDate(LocalDateTime treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) { this.treatmentDescription = treatmentDescription; }

    public Boolean getTreatmentNotification() {
        return treatmentNotification;
    }

    public void setTreatmentNotification(Boolean treatmentNotification) {
        this.treatmentNotification = treatmentNotification;
    }

    public LocalDateTime getTreatmentAlertTime() {
        return treatmentAlertTime;
    }

    public String getTreatmentAlertTimeString() { return treatmentAlertTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")); }

    public LocalDateTime getTreatmentAlertTimeFromString(String stringTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        final LocalDateTime localDateTime = LocalDateTime.parse(stringTime, formatter);
        return localDateTime;
    }

    public void setTreatmentAlertTime(LocalDateTime treatmentAlertTime) {
        this.treatmentAlertTime = treatmentAlertTime;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

}