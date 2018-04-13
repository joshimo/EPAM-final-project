package com.epam.project.dao;

import com.epam.project.entities.User;

import java.util.List;

public interface IUserDao {

    List<User> retrieveAllUsersFromDB();

    User retrieveUserById(Integer id);

    boolean addUserToDB(User user) throws Exception;

    boolean updateUserInDB(User user) throws Exception;

    boolean deleteUserFromDB(User user) throws Exception;

}
