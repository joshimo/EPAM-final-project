package com.epam.project.dao;

import com.epam.project.entities.User;

import java.util.List;

public interface IUserDao {

    <T extends Exception> List<User> findAllUsersInDB() throws T;

    <T extends Exception> User findUserById(Integer id) throws T;

    <T extends Exception> User findUserByName(String name) throws T;

    <T extends Exception> boolean addUserToDB(User user) throws T;

    <T extends Exception> boolean updateUserInDB(User user) throws T;

    <T extends Exception> boolean deleteUserFromDB(User user) throws T;

}
