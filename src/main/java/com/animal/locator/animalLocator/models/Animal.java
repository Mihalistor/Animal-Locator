package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "animal_id")
    private int animalId;

    @Column(name = "name")
    private String animalName;

    @Column(name = "birthday")
    private LocalDateTime animalBirthday;

    @Column(name = "sex")
    @Enumerated
    private AnimalSex animalSex;

    @Column(name = "transponder_code")
    private String animalTransponderCode;

    @Column(name = "transponder_location")
    private String animalTransponderLocation;

    @Column(name = "passport_code")
    private String animalPassportCode;

    @Column(name = "lastNotification")
    private LocalDateTime lastNotification;

    @Column(name = "avatar")
    private String avatar = "img/default.jpg";

    @OneToOne
    private GpsLocator gpsLocator;

    @ManyToOne
    private User user;

    @ManyToOne
    private Color color;

    @ManyToOne
    private Species species;

    @ManyToOne
    private Breed breed;

    public Animal() {
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public LocalDateTime getAnimalBirthday() {
        return animalBirthday;
    }

    public String getAnimalBirthdayString() { return animalBirthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")); }

    public LocalDateTime getAnimalBirthdayFromString(String stringTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        final LocalDateTime localDateTime = LocalDateTime.parse(stringTime, formatter);
        return localDateTime;
    }

    public void setAnimalBirthday(LocalDateTime animalBirthday) {
        this.animalBirthday = animalBirthday;
    }

    public AnimalSex getAnimalSex() {
        return animalSex;
    }

    public void setAnimalSex(AnimalSex animalSex) {
        this.animalSex = animalSex;
    }

    public String getAnimalTransponderCode() {
        return animalTransponderCode;
    }

    public void setAnimalTransponderCode(String animalTransponderCode) {
        this.animalTransponderCode = animalTransponderCode;
    }

    public String getAnimalTransponderLocation() {
        return animalTransponderLocation;
    }

    public void setAnimalTransponderLocation(String animalTransponderLocation) {
        this.animalTransponderLocation = animalTransponderLocation;
    }

    public String getAnimalPassportCode() {
        return animalPassportCode;
    }

    public void setAnimalPassportCode(String animalPassportCode) {
        this.animalPassportCode = animalPassportCode;
    }

    public LocalDateTime getLastNotification() {
        return lastNotification;
    }

    public void setLastNotification(LocalDateTime lastNotification) {
        this.lastNotification = lastNotification;
    }

    public GpsLocator getGpsLocator() {
        return gpsLocator;
    }

    public void setGpsLocator(GpsLocator gpsLocator) {
        this.gpsLocator = gpsLocator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

}