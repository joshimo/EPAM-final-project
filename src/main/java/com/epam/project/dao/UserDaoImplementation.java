package com.epam.project.dao;

import com.epam.project.entities.User;
import com.epam.project.entities.UserRole;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoImplementation implements IUserDao {

    private static String SQL_selectAll = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id;";
    private static String SQL_selectById = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_id=?;";
    private static String SQL_selectByName = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_name=?;";
    private static String SQL_addNew = "INSERT INTO project.users " +
            "(user_name, user_password, role_id, user_notes) VALUES (?,?,?,?)";
    private static String SQL_updateExist = "UPDATE project.users SET " +
            "user_name=?, user_password=?, role_id=?, user_notes=? WHERE user_name=?;";

    private Mapper<User, PreparedStatement> mapperToDB = (User user, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getUserRole().ordinal() + 1);
        preparedStatement.setString(4, user.getNotes());
    };

    private Mapper<ResultSet, User> mapperFromDB = (ResultSet resultSet, User user) -> {
        user.setId(resultSet.getInt("user_id"));
        user.setName(resultSet.getString("user_name"));
        user.setPassword(resultSet.getString("user_password"));
        user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
        user.setNotes(resultSet.getString("user_notes"));
    };

    public UserDaoImplementation() {
    }

    @Override
    public List<User> findAllUsersInDB() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<User> users = new LinkedList<>();
        Connection connection = Connector.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                mapperFromDB.map(resultSet, user);
                users.add(user);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        } finally {
            Connector.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User findUserById(Integer id) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        User user = new User();
        Connection connection = Connector.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, user);
            else
                throw new DataNotFoundException();
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        } finally {
            Connector.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findUserByName(String name) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        User user = new User();
        Connection connection = Connector.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByName);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, user);
            else
                throw new DataNotFoundException();
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        } finally {
            Connector.closeConnection(connection);
        }
        return user;
    }

    @Override
    public boolean addUserToDB(User user) throws IncorrectPropertyException, DataBaseConnectionException  {
        boolean result = false;
        Connection connection = Connector.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_addNew);
            mapperToDB.map(user, preparedStatement);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            result = false;
        } finally {
            Connector.closeConnection(connection);
            return result;
        }
    }

    @Override
    public boolean updateUserInDB(User user) throws IncorrectPropertyException, DataBaseConnectionException   {
        boolean result = false;
        Connection connection = Connector.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_updateExist);
            preparedStatement.setString(5, user.getName());
            mapperToDB.map(user, preparedStatement);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            result = false;
        } finally {
            Connector.closeConnection(connection);
            return result;
        }
    }

    @Override
    public <T extends Exception> boolean deleteUserFromDB(User user) throws T {
        return false;
    }
}
