package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Timestamp;
import java.util.List;

public class UserDaoTester {

    private static IUserDao userDao;
    private static List<User> users;


    public UserDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        userDao = daoFactory.getUserDao();
    }

    public static void main(String... args) throws Exception {
        UserDaoTester userDaoTester = new UserDaoTester();
        //userDaoTester.testFindUsers("Yaroslav");
        //System.out.println("User created: " + userDaoTester.testAddUser(userDaoTester.createTestUser()));
        System.out.println("User updated: " + userDaoTester.testUpdateUser("Yaroslav"));
    }

    private User createTestUser() {
        User user = new User("Somebody", "else");
        user.setUserRole(UserRole.USER);
        user.setNotes("Created by " + this.getClass().getSimpleName());
        return user;
    }

    private void testFindUsers(String name) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        System.out.println("All users\n");
        System.out.println(userDao.findAllUsersInDB());
        System.out.println("\nUser by name = " + name);
        System.out.println(userDao.findUserByName(name));
    }

    private boolean testAddUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.addUserToDB(user);
    }

    private boolean testUpdateUser(String name) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        User user = userDao.findUserByName(name);
        user.setUserRole(UserRole.ADMIN);
        user.setPassword("yaroslav");
        user.setNotes("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        return userDao.updateUserInDB(user);
    }

    private boolean testDeleteUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.deleteUserFromDB(user);
    }
}
