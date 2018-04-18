package com.epam.project.entities;

/** 'User' Enity is mapped both to project.users & project.user_roles tables*/
public class User {

    private Integer id;
    private String name;
    private String password;
    private UserRole userRole;
    private String notes;

    public User() {

    }

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

    public UserRole getUserRole() {
        return userRole;
    }

    public String getNotes() {
        return notes;
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

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    @Override
    public String toString() {
        return  "\nUser ID = " + id +
                "\nName: " + name +
                "\nPassword: " + password +
                "\nRole: " + userRole +
                "\nNotes: " + notes +
                "\n---------------------------------------------------------------------------------------------------";
    }
}
