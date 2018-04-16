package com.epam.project.entities;

/** 'User' Enity is mapped to project.users table*/
public class User {
    private Integer id;
    private String name;
    private String password;
    private Boolean isClient;
    private Boolean isCashier;
    private Boolean isSeniorCashier;
    private Boolean isMerchant;
    private String notes;
    private UserRole userRole;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /** Getters */
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getClient() {
        return isClient;
    }

    public Boolean getCashier() {
        return isCashier;
    }

    public Boolean getSeniorCashier() {
        return isSeniorCashier;
    }

    public Boolean getMerchant() {
        return isMerchant;
    }

    public String getNotes() {
        return notes;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    /** Setters */
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setClient(Boolean client) {
        isClient = client;
    }

    public void setCashier(Boolean cashier) {
        isCashier = cashier;
    }

    public void setSeniorCashier(Boolean seniorCashier) {
        isSeniorCashier = seniorCashier;
    }

    public void setMerchant(Boolean merchant) {
        isMerchant = merchant;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    @Override
    public String toString() {
        return  "User ID = " + id +
                "\nName: " + name +
                "\nPassword: " + password +
                "\nRole: " + userRole +
                "\nNotes: " + notes +
                "\n---------------------------------------------------------------------------------------------------";
    }
}
