package com.epam.project.service;

import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.UnknownUserException;

import java.util.List;

public interface IUserServ {

    Integer calculateUsersNumber();

    List<User> findAllUsers() throws UnknownUserException;

    @Button
    List<User> findUsers(Integer from, Integer offset) throws UnknownUserException;

    List<User> findUsersByRole(UserRole userRole) throws UnknownUserException;

    User findUser(String name, String password) throws UnknownUserException;

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
