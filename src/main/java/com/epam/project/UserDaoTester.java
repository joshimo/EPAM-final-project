package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.domain.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

public class UserDaoTester {

    private static IUserDao userDao;
    private static List<User> users;

    private static final Logger log = Logger.getLogger(ProductDaoTester.class);

    public UserDaoTester() throws DataBaseNotSupportedException, IncorrectPropertyException, DataBaseConnectionException {
        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        userDao = daoFactory.getUserDao();
    }

    public static void main(String... args) throws Exception {
        UserDaoTester userDaoTester = new UserDaoTester();
        //userDaoTester.testFindUsers("Yaroslav");
        //log.info("User created: " + userDaoTester.testAddUser(userDaoTester.createTestUser()));
        log.info("User updated: " + userDaoTester.testUpdateUser("Yaroslav"));
    }

    private User createTestUser() {
        User user = new User("Somebody", "else");
        user.setUserRole(UserRole.USER);
        user.setNotes("Created by " + this.getClass().getSimpleName());
        return user;
    }

    private void testFindUsers(String name) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        log.info("All users\n");
        log.info(userDao.findAllUsersInDB());
        log.info("\nUser by name = " + name);
        log.info(userDao.findUserByName(name));
    }

    private boolean testAddUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.addUserToDB(user);
    }

    private boolean testUpdateUser(String name) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        User user = userDao.findUserByName(name);
        user.setUserRole(UserRole.ADMIN);
        user.setName("Yarik");
        user.setPassword("Yarik");
        user.setNotes("Updated by " + this.getClass().getSimpleName() + " at " + new Timestamp(System.currentTimeMillis()));
        return userDao.updateUserInDB(user);
    }

    private boolean testDeleteUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.deleteUserFromDB(user);
    }
}
