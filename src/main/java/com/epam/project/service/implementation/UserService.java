package com.epam.project.service.implementation;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IUserDao;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.*;
import com.epam.project.service.Button;
import com.epam.project.service.IUserServ;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class UserService implements IUserServ {

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

    @Button
    public User findUser(String name, String password) throws UnknownUserException{
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

    @Button
    public List<User> findAllUsers() throws UnknownUserException {
        List<User> users = new LinkedList<>();
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findAllUsersInDB();
            daoFactory.close();
            return users;
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
        }
    }

    @Button
    public List<User> findUsersByRole(UserRole userRole) throws UnknownUserException {
        List<User> users = new LinkedList<>();
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findUserByRole(userRole);
            daoFactory.close();
            return users;
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
        }
    }

    /** User validation method to check user before storing in DB */

    public boolean validateUserData(User user) {
        return !(user.getName() == null
                || user.getName().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getUserRole() == null);
    }

    public boolean checkIfSuperAdmin(String name, String password) {
        return name.equals(SUPER_ADMIN_NAME) && password.equals(SUPER_ADMIN_PASSWORD);
    }

    /** Data access and storing methods */

    @Button
    public boolean addUser(User user) {
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

    @Button
    public boolean updateUser(User user) {
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

    @Button
    public boolean deleteUser(User user) {
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
