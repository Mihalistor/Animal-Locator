package com.animal.locator.animalLocator.forms;

import com.animal.locator.animalLocator.models.User;

import java.util.ArrayList;
import java.util.List;

public class CreateAnimalForm {

    private User user;

    private List<User> userList = new ArrayList<>();

    private int numberOfAnimals  = 1;

    private String devices;

    private String tokens;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

}