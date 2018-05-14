package com.epam.project.dao.implementation;

import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.IUserDao;
import com.epam.project.dao.Mapper;
import com.epam.project.domain.User;
import com.epam.project.domain.UserRole;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoImpl extends GenericAbstractDao<User> implements IUserDao {
    private Connection connection;
    private static String SQL_selectAll = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id;";
    private static String SQL_selectById = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_id=?;";
    private static String SQL_selectByName = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_name=?;";
    private static String SQL_selectByRole = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_roles.role_description=?;";
    private static String SQL_addNew = "INSERT INTO project.users " +
            "(user_name, user_password, user_phone, user_email, user_address, role_id, user_notes) " +
            "VALUES (?,?,?,?,?,?,?)";
    private static String SQL_updateByName = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_email=?, user_address=?, role_id=?, user_notes=? " +
            "WHERE user_name=?;";
    private static String SQL_updateById = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_email=?, user_address=?, role_id=?, user_notes=? " +
            "WHERE user_id=?;";
    private static String SQL_deleteUser = "DELETE FROM project.users WHERE user_name=?;";

    private Mapper<User, PreparedStatement> mapperToDB = (User user, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getPhoneNumber());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getAddress());
        preparedStatement.setInt(6, user.getUserRole().ordinal());
        preparedStatement.setString(7, user.getNotes());
    };

    private Mapper<ResultSet, User> mapperFromDB = (ResultSet resultSet, User user) -> {
        user.setId(resultSet.getInt("user_id"));
        user.setName(resultSet.getString("user_name"));
        user.setPassword(resultSet.getString("user_password"));
        user.setPhoneNumber(resultSet.getString("user_phone"));
        user.setEmail(resultSet.getString("user_email"));
        user.setAddress(resultSet.getString("user_address"));
        user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
        user.setNotes(resultSet.getString("user_notes"));
    };

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<User> findAllUsersInDB() throws DataNotFoundException {
        List<User> users = findAll(connection, User.class, SQL_selectAll);
        return users;
    }

    @Override
    public List<User> findUserByRole(UserRole role) throws DataNotFoundException {
        List<User> users = findAsListBy(connection, User.class, SQL_selectByRole, role.toString());
        return users;
    }

    @Override
    public User findUserById(Integer id) throws DataNotFoundException {
        User user = findBy(connection, User.class, SQL_selectById, id);
        return user;
    }

    @Override
    public User findUserByName(String name) throws DataNotFoundException {
        User user = findBy(connection, User.class, SQL_selectByName, name);
        return user;
    }

    @Override
    public boolean addUserToDB(User user) {
        boolean result = addToDB(connection, user, SQL_addNew);
        return result;
    }

    @Override
    public boolean updateUserInDB(User user) {
        boolean result = updateInDB(connection, user, SQL_updateById, 8, user.getId());
        return result;
    }

    @Override
    public boolean deleteUserFromDB(User user) {
        boolean result = deleteFromDB(connection, SQL_deleteUser, user.getName());
        return result;
    }
}