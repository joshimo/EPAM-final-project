package com.epam.project.dao;

import com.epam.project.entities.User;
import com.epam.project.entities.UserRole;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.DataNotFoundException;
import com.epam.project.exceptions.IncorrectPropertyException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoImpl extends GenericAbstractDao<User> implements IUserDao{
    private static String SQL_selectAll = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id;";
    private static String SQL_selectById = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_id=?;";
    private static String SQL_selectByName = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_name=?;";
    private static String SQL_addNew = "INSERT INTO project.users " +
            "(user_name, user_password, role_id, user_notes) VALUES (?,?,?,?)";
    private static String SQL_updateByName = "UPDATE project.users SET " +
            "user_name=?, user_password=?, role_id=?, user_notes=? WHERE user_name=?;";
    private static String SQL_updateById = "UPDATE project.users SET " +
            "user_name=?, user_password=?, role_id=?, user_notes=? WHERE user_id=?;";
    private static String SQL_deleteUser = "DELETE FROM project.users WHERE user_name=?;";

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

    public UserDaoImpl() {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public List<User> findAllUsersInDB() throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException {
        List<User> users = findAll(User.class, SQL_selectAll);
        return users;
    }

    @Override
    public User findUserById(Integer id) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        User user = findBy(User.class, SQL_selectById, id);
        return user;
    }

    @Override
    public User findUserByName(String name) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        User user = findBy(User.class, SQL_selectByName, name);
        return user;
    }

    @Override
    public boolean addUserToDB(User user) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        return addToDB(user, SQL_addNew);
    }

    @Override
    public boolean updateUserInDB(User user) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        return updateInDB(user, SQL_updateByName, 5, user.getName());
    }

    @Override
    public boolean deleteUserFromDB(User user) throws IncorrectPropertyException, DataBaseConnectionException, DataNotFoundException  {
        return deleteFromDB(SQL_deleteUser, user.getName());
    }
}
