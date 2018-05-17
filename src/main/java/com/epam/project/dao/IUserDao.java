package com.epam.project.dao;

import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.DataNotFoundException;

import java.util.List;

/**
 * CRUD operations interface for User entity
 */
public interface IUserDao {

    /**
     * Calculates total users number available in DB
     * @return count of users in DB
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Integer calculateUsersNumber() throws DataNotFoundException;

    /**
     * Finds all users in DB
     * @return List of all users
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findAllUsersInDB() throws DataNotFoundException;

    /**
     * Finds users in DB from
     * @param first first row number
     * @param offset offset
     * @return List users
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findUsers(Integer first, Integer offset) throws DataNotFoundException;

    /**
     * Finds all users by user role
     * @param role - User`s role
     * @return List of
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findUserByRole(UserRole role) throws DataNotFoundException;

    /**
     * Finds user by user id number
     * @param id - User`s id number
     * @return User
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserById(Integer id) throws DataNotFoundException;

    /**
     * Finds User by name
     * @param name - User name
     * @return User by name
     * @throws DataNotFoundException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserByName(String name) throws DataNotFoundException;

    /**
     * Adds new user to DB
     * @param user - user to add in DB
     * @return true if operation success and false if fails
     */
    boolean addUserToDB(User user);

    /**
     * Updats user in DB
     * @param user - User to update in DB
     * @return true if operation success and false if fails
     */
    boolean updateUserInDB(User user);

    /**
     * Deletes user from DB
     * @param user - User to delete from DB
     * @return true if operation success and false if fails
     */
    boolean deleteUserFromDB(User user);
}
