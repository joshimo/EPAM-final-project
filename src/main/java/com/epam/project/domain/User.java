package com.epam.project.domain;

import java.io.Serializable;

/** 'User' Enity is mapped both to project.users & project.user_roles tables*/
public class User implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private String phoneNumber;
    private String email;
    private String address;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return  "\nUser ID = " + id +
                "\nName: " + name +
                "\nPassword: " + password +
                "\nPhone: " + phoneNumber +
                "\ne-mail: " + email +
                "\nAddress: " + address +
                "\nRole: " + userRole +
                "\nNotes: " + notes +
                "\n---------------------------------------------------------------------------------------------------";
    }
}