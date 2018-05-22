package com.epam.project.service;

import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.UnknownUserException;

import java.util.List;

public interface IUserServ {

    /**
     * Calculates total number of User records in DB
     * @return total number of User records in DB
     */
    Integer calculateUsersNumber();

    /**
     * Finds all users in DB
     * @return list of all users
     * @throws UnknownUserException if unable to retrieve information for certain reasons
     */
    List<User> findAllUsers() throws UnknownUserException;

    /**
     * Finds first @param offset products starts from @param from row
     * @param from - first table line number
     * @param offset - number of records to find
     * @return List of users
     * @throws UnknownUserException if unable to retrieve information for certain reasons
     */
    @Button
    List<User> findUsers(Integer from, Integer offset) throws UnknownUserException;

    /**
     * Finds all users by User Role
     * @param userRole - User role
     * @return List of all users with @param userRole
     * @throws UnknownUserException if unable to retrieve information for certain reasons
     */
    List<User> findUsersByRole(UserRole userRole) throws UnknownUserException;

    /**
     * Finds user by User name and password
     * @param name - User name
     * @param password - user password
     * @return user found by username and password
     * @throws UnknownUserException if unable to retrieve information for certain reasons
     */
    User findUser(String name, String password) throws UnknownUserException;

    /**
     * Adds a new user in DB
     * @param user - user to add
     * @return true if operation success and false if fails
     */
    boolean addUser(User user);

    /**
     * Updates existent user data in DB
     * @param user -  user to update
     * @return true if operation success and false if fails
     */
    boolean updateUser(User user);

    /**
     * Deletes user from DB
     * @param user - user to delete
     * @return true if operation success and false if fails
     */
    boolean deleteUser(User user);
}
