package com.epam.project.logic;

import com.epam.project.dao.DaoFactory;
import com.epam.project.dao.DataBaseSelector;
import com.epam.project.dao.IUserDao;
import com.epam.project.entities.User;
import com.epam.project.exceptions.*;

public class UserLogic {

    private static final String SUPER_ADMIN_NAME = "Yaroslav";
    private static final String SUPER_ADMIN_PASSWORD = "golota";
    private static IUserDao userDao;

    UserLogic() throws DataBaseNotSupportedException, IncorrectPropertyException {
        userDao = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL).getUserDao();
    }

    static User findUser(String name, String password) throws IncorrectPropertyException, DataBaseConnectionException, UnknownUserException {
        try {
            User user = userDao.findUserByName(name);
            if (user.getPassword().equals(password))
                return user;
            throw new UnknownUserException("User " + name + " not found!");
        }  catch (DataNotFoundException dnfe) {
            throw new UnknownUserException("User " + name + " not found!");
        }
    }

    static boolean checkIfSuperAdmin(String name, String password) {
        return name.equals(SUPER_ADMIN_NAME) && password.equals(SUPER_ADMIN_PASSWORD);
    }

    static boolean addUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.addUserToDB(user);
    }

    static boolean updateUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.updateUserInDB(user);
    }

    static boolean deleteUser(User user) throws IncorrectPropertyException, DataBaseConnectionException {
        return userDao.deleteUserFromDB(user);
    }

}
