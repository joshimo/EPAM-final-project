package com.epam.project.dao;

import com.epam.project.entities.User;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.util.List;

public interface IUserDao {

    List<User> findAllUsersInDB() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException;

    User findUserById(Integer id) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException;

    User findUserByName(String name) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException;

    boolean addUserToDB(User user) throws IncorrectPropertyException, DataBaseConnectionException;

    boolean updateUserInDB(User user) throws IncorrectPropertyException, DataBaseConnectionException;

    boolean deleteUserFromDB(User user) throws IncorrectPropertyException, DataBaseConnectionException;

}
