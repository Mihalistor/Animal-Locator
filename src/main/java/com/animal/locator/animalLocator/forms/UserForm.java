package com.animal.locator.animalLocator.forms;

public class UserForm {

    String firstname;

    String lastname;

    String username;

    String password;

    String cpassword;

    String email;

    String phone;

    Boolean emailNotification;

    Boolean SMSNotification;

    Boolean mobileNotification;

    Boolean deleteData;

    int delay;

    Boolean changePassword = false;

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

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
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

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Boolean getSMSNotification() {
        return SMSNotification;
    }

    public void setSMSNotification(Boolean SMSNotification) {
        this.SMSNotification = SMSNotification;
    }

    public Boolean getMobileNotification() {
        return mobileNotification;
    }

    public void setMobileNotification(Boolean mobileNotification) {
        this.mobileNotification = mobileNotification;
    }

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

    public Boolean getDeleteData() {
        return deleteData;
    }

    public void setDeleteData(Boolean deleteData) {
        this.deleteData = deleteData;
    }

}
