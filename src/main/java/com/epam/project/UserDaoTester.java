package com.epam.project;

import com.epam.project.dao.*;
import com.epam.project.entities.*;
import com.epam.project.exceptions.DataBaseNotSupportedException;
import com.epam.project.exceptions.IncorrectPropertyException;

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
        userDaoTester.testUserDao();
    }

    public void testUserDao() {
        User user = new User("Somebody", "else");
        user.setUserRole(UserRole.USER);
        user.setNotes("Added by UserDaoTester runner");
        //System.out.println(userDao.addUserToDB(user));
        //System.out.println(userDao.updateUserInDB(user));
        System.out.println(userDao.deleteUserFromDB(user));
        System.out.println("All users\n");
        users = userDao.findAllUsersInDB();
        System.out.println(users);
        System.out.println("\nUser by name = Yaroslav");
        System.out.println(userDao.findUserByName("Yaroslav"));
    }
}
