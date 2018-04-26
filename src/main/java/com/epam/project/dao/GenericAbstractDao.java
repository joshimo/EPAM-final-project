package com.epam.project.dao;

import com.epam.project.exceptions.DataNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public abstract class GenericAbstractDao<T> {

    protected Mapper<T, PreparedStatement> mapperToDB;
    protected Mapper<ResultSet, T> mapperFromDB;

    GenericAbstractDao() {
    }

    public void setMapperToDB(Mapper<T, PreparedStatement> mapperToDB) {
        this.mapperToDB = mapperToDB;
    }

    public void setMapperFromDB(Mapper<ResultSet, T> mapperFromDB) {
        this.mapperFromDB = mapperFromDB;
    }

    List<T> findAll(Connection connection, Class t, String SQL_getAll) throws DataNotFoundException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_getAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return items;
    }

    @Deprecated
    public T _findBy(Connection connection, Class t, String SQL_selectByParameter, Integer parameter)
            throws DataNotFoundException {
        T item = getItemInstance(t);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            preparedStatement.setInt(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, item);
            else
                throw new DataNotFoundException();
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return item;
    }

    @Deprecated
    public T _findBy(Connection connection, Class t, String SQL_selectByParameter, String parameter)
            throws DataNotFoundException {
        T item = getItemInstance(t);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            preparedStatement.setString(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, item);
            else
                throw new DataNotFoundException();
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return item;
    }

    public <V> T findBy(Connection connection, Class t, String SQL_selectByParameter, V value) throws DataNotFoundException {
        T item = getItemInstance(t);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                mapperFromDB.map(resultSet, item);
            else
                throw new DataNotFoundException();
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return item;
    }

    @Deprecated
    public List<T> _findAsListBy(Connection connection, Class t, String SQL_selectByParameter, Long parameter)
            throws DataNotFoundException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            preparedStatement.setLong(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return items;
    }

    @Deprecated
    public List<T> _findAsListBy(Connection connection, Class t, String SQL_selectByParameter, String parameter)
            throws DataNotFoundException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            preparedStatement.setString(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return items;
    }

    public <V> List<T> findAsListBy(Connection connection, Class t, String SQL_selectByParameter, V value) throws DataNotFoundException {
        List<T> items = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_selectByParameter);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(t);
                mapperFromDB.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException sqle) {
            throw new DataNotFoundException();
        }
        return items;
    }

    public boolean addToDB(Connection connection, T item, String SQL_addNew) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_addNew);
            mapperToDB.map(item, preparedStatement);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            return false;
        }
        return result;
    }

    @Deprecated
    public boolean _updateInDB(Connection connection, T item, String SQL_update, Integer paramNum, Integer param) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_update);
            mapperToDB.map(item, preparedStatement);
            preparedStatement.setInt(paramNum, param);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            result = false;
        } finally {
            return result;
        }
    }

    @Deprecated
    public boolean _updateInDB(Connection connection, T item, String SQL_update, Integer paramNum, String param) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_update);
            mapperToDB.map(item, preparedStatement);
            preparedStatement.setString(paramNum, param);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            result = false;
        } finally {
            return result;
        }
    }

    public <V> boolean updateInDB(Connection connection, T item, String SQL_update, Integer paramNum, V value) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_update);
            mapperToDB.map(item, preparedStatement);
            addParameterToPreparedStatement(preparedStatement, paramNum, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            return false;
        }
        return result;
    }

    @Deprecated
    public boolean _deleteFromDB(Connection connection, String SQL_delete, Integer id) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_delete);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            result = false;
        } finally {
            return result;
        }
    }

    @Deprecated
    public boolean _deleteFromDB(Connection connection, String SQL_delete, String param) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_delete);
            preparedStatement.setString(1, param);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            result = false;
        } finally {
            return result;
        }
    }

    public <V> boolean deleteFromDB(Connection connection, String SQL_delete, V value) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_delete);
            addParameterToPreparedStatement(preparedStatement, 1, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqle) {
            return false;
        }
        return result;
    }

    /** Private method witch returns a concrete instance on entity */
    private T getItemInstance(Class t) {
        T item = null;
        try {
            item = (T) t.newInstance();
        } catch (InstantiationException ie) { }
        catch (IllegalAccessException iae) { }
        return item;
    }

    private <V> void addParameterToPreparedStatement(PreparedStatement preparedStatement, Integer paramNum, V value) throws SQLException {
        if (value instanceof String)
            preparedStatement.setString(paramNum, (String) value);
        if (value instanceof Integer)
            preparedStatement.setInt(paramNum, (Integer) value);
        if (value instanceof Long)
            preparedStatement.setLong(paramNum, (Long) value);
    }
}