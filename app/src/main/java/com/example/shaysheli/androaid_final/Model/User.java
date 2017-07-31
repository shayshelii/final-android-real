package com.example.shaysheli.androaid_final.Model;

/**
 * Created by ShaySheli on 29/07/2017.
 */

public class User {
    public String id;
    public String name;
    public String email;
    public Boolean isAdmin;
    public String phoneNumber;

    public User () {

    }

    public User(String name, String email, String phoneNumber, Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
        this.phoneNumber = phoneNumber;
    }

    public User(User other) {
        this.name = other.getName();
        this.email = other.getEmail();
        this.isAdmin = other.getAdmin();
        this.phoneNumber = other.getPhoneNumber();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
