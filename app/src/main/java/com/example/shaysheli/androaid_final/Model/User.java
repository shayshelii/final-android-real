package com.example.shaysheli.androaid_final.Model;

/**
 * Created by ShaySheli on 29/07/2017.
 */

public class User {
    public String id;
    public String name;
    public String email;
    public Boolean isAdmin;

    public User () {

    }

    public User(String name, String email, Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public User(User other) {
        this.name = other.getName();
        this.email = other.getEmail();
        this.isAdmin = other.getAdmin();
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
}
