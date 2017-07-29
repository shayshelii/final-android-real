package com.example.shaysheli.androaid_final.Model;

/**
 * Created by ShaySheli on 29/07/2017.
 */

public class User {
    public String id;
    public String name;
    public String email;
    public String phone;
    public String location;
    public String password;
    public Boolean isAdmin;

    public User(String name, String email, String phone, String location, String password, Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
