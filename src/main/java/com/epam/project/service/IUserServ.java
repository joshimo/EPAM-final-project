package com.epam.project.service;

import com.epam.project.domain.User;
import com.epam.project.exceptions.UnknownUserException;

public interface IUserServ {

    User findUser(String name, String password) throws UnknownUserException;

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
