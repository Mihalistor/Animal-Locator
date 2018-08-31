package com.animal.locator.animalLocator.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "user_id")
    private int userId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "delay", columnDefinition = "INT DEFAULT 15")
    private int delay = 15;

    @Column(name = "emailNotification", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean emailNotification = true;

    @Column(name = "mobileNotification", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean mobileNotification = false;

    @Column(name = "SMSNotification", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean SMSNotification = false;

    @Column(name = "status", columnDefinition = "INT DEFAULT 0")
    private int status = 0;

    @Column (name = "deleteData", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean deleteData = true;

    @Lob
    @Column(name="avatar")
    private byte[] avatar;

    public User() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Boolean getMobileNotification() {
        return mobileNotification;
    }

    public void setMobileNotification(Boolean mobileNotification) {
        this.mobileNotification = mobileNotification;
    }

    public Boolean getSMSNotification() {
        return SMSNotification;
    }

    public void setSMSNotification(Boolean SMSNotification) {
        this.SMSNotification = SMSNotification;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getDeleteData() {
        return deleteData;
    }

    public void setDeleteData(Boolean deleteData) {
        this.deleteData = deleteData;
    }

}