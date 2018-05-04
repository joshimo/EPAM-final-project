package com.epam.project.service;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IUserDao;
import com.epam.project.domain.User;
import com.epam.project.exceptions.*;
import org.apache.log4j.Logger;

public class UserService {

    private static final String SUPER_ADMIN_NAME = "Yaroslav";
    private static final String SUPER_ADMIN_PASSWORD = "golota";
    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;
    private static final Logger log = Logger.getLogger(UserService.class);
    private static DaoFactory daoFactory;
    private static IUserDao userDao;

    static {
        try {
            daoFactory = DaoFactory.getDaoFactory(source);
            userDao = daoFactory.getUserDao();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
        }
    }

    public static User findUser(String name, String password) throws UnknownUserException{
        User user = new User();
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            user = userDao.findUserByName(name);
            daoFactory.close();
            if (!user.getPassword().equals(password))
                throw new UnknownUserException();
            return user;
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
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

    public static boolean checkIfSuperAdmin(String name, String password) {
        return name.equals(SUPER_ADMIN_NAME) && password.equals(SUPER_ADMIN_PASSWORD);
    }

    /** Data access and storing methods */

    public static boolean addUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = validateUserData(user) && userDao.addUserToDB(user);
            daoFactory.close();
        } catch (IncorrectPropertyException | DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    public static boolean updateUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = validateUserData(user) && userDao.updateUserInDB(user);
            daoFactory.close();
        } catch (IncorrectPropertyException | DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    public static boolean deleteUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = validateUserData(user) && userDao.deleteUserFromDB(user);
            daoFactory.close();
        } catch (IncorrectPropertyException | DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }
}
