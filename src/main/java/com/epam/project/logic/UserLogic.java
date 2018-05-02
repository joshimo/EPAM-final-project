package com.epam.project.logic;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IUserDao;
import com.epam.project.domain.User;
import com.epam.project.exceptions.*;
import org.apache.log4j.Logger;

public class UserLogic {

    private static final String SUPER_ADMIN_NAME = "Yaroslav";
    private static final String SUPER_ADMIN_PASSWORD = "golota";
    private static IUserDao userDao;

    private static final Logger log = Logger.getLogger(UserLogic.class);

    static {
        try {
            userDao = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL).getUserDao();
        } catch (IncorrectPropertyException ipe) {
            log.error(ipe);
        } catch (DataBaseNotSupportedException dbnse) {
            log.error(dbnse);
        }
    }

    public static User findUser(String name, String password) throws IncorrectPropertyException, DataBaseConnectionException, UnknownUserException {
        try {
            User user = userDao.findUserByName(name);
            if (user.getPassword().equals(password))
                return user;
            throw new UnknownUserException("User " + name + " not found!");
        }  catch (DataNotFoundException dnfe) {
            throw new UnknownUserException("User " + name + " not found!");
        }
    }

    /** User validation method to check user before storing in DB */

    public static boolean validateUserData(User user) {
        return !(user.getName() == null
                || user.getName().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getUserRole() == null);
    }

    /** Data access and storing methods */
    static boolean checkIfSuperAdmin(String name, String password) {
        return name.equals(SUPER_ADMIN_NAME) && password.equals(SUPER_ADMIN_PASSWORD);
    }

    static boolean addUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        if (validateUserData(user))
            return userDao.addUserToDB(user);
        return false;
    }

    static boolean updateUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        if (validateUserData(user))
            return userDao.updateUserInDB(user);
        return false;
    }

    static boolean deleteUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.deleteUserFromDB(user);
    }

}
