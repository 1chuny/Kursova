package com.example.mbd.model;


public class User {

    private Integer userId;
    private String name;
    private String login;
    private String passwordHash;
    private boolean isAdmin;
    private boolean isLogged;

    public User(Integer userId, String name, String login, String passwordHash, Boolean isAdmin, Boolean isLogged) {
        this.userId = userId;
        this.name = name;
        this.login = login;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
        this.isLogged = isLogged;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean isAdmin) {
        this.isLogged = isLogged;
    }
}
