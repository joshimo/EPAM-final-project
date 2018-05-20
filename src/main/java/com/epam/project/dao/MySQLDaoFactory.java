package com.epam.project.dao;

import com.epam.project.dao.implementation.*;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySQLDaoFactory extends DaoFactory {

    private static BasicDataSource basicDataSource;
    private static final Logger log = Logger.getLogger(MySQLDaoFactory.class);
    private Connection connection;

    MySQLDaoFactory() throws IncorrectPropertyException {
        String user;
        String password;
        String host;
        String port;
        String database;
        String useUnicode;
        String encoding;
        String url;
        Integer minIdle;
        Integer maxIdle;
        Integer maxActive;
        Integer maxOpenPStatements;
        ResourceBundle dbConfig = ResourceBundle.getBundle("dbConfig");
        try {
            user = dbConfig.getString("user");
            password = dbConfig.getString("password");
            host = dbConfig.getString("host");
            port = dbConfig.getString("port");
            database = dbConfig.getString("database");
            useUnicode = dbConfig.getString("useUnicode");
            encoding = dbConfig.getString("encoding");
            url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=" + useUnicode + "&characterEncoding=" + encoding;
            minIdle = Integer.parseInt(dbConfig.getString("minIdle"));
            maxIdle = Integer.parseInt(dbConfig.getString("maxIdle"));
            maxActive = Integer.parseInt(dbConfig.getString("maxActive"));
            maxOpenPStatements = Integer.parseInt(dbConfig.getString("maxOpenPreparedStatements"));
        } catch (NullPointerException | NumberFormatException npe) {
            log.error(npe);
            throw new IncorrectPropertyException("Incorrect db property");
        }
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setMinIdle(minIdle);
        basicDataSource.setMaxIdle(maxIdle);
        basicDataSource.setMaxActive(maxActive);
        basicDataSource.setMaxOpenPreparedStatements(maxOpenPStatements);
    }

    private static Connection getConnection() throws DataBaseConnectionException {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataBaseConnectionException();
        }
    }

    /** Transaction methods */
    public void beginTransaction() throws DataBaseConnectionException {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataBaseConnectionException();
        }
    }

    public void commitTransaction() throws DataBaseConnectionException {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataBaseConnectionException();
        }
    }

    public void rollbackTransaction() throws DataBaseConnectionException {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException sqle) {
            log.error(sqle);
            throw new DataBaseConnectionException();
        }
    }

    /** Connection open and closing methods */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException sqle) {
            log.error(sqle);
        }
    }

    @Override
    public void open() throws DataBaseConnectionException {
        connection = getConnection();
    }

    @Deprecated
    public static void closeConnection(Connection connection) throws DataBaseConnectionException {
        try {
            connection.close();
        } catch (SQLException sqle) {
            log.error(sqle);
        } catch (NullPointerException npe) {
            log.error(npe);
        }
    }

    @Override
    void closeConnection() throws DataBaseConnectionException {
        try {
            connection.close();
        } catch (SQLException sqle) {
            log.error(sqle);
        } catch (NullPointerException npe) {
            log.error(npe);
        }
    }

    @Override
    public IUserDao getUserDao() {
        return new UserDaoImpl(connection);
    }

    @Override
    public IProductDao getProductDao() {
        return new ProductDaoImpl(connection);
    }

    @Override
    public IInvoiceDao getInvoiceDao() {
        return new InvoiceDaoImpl(connection);
    }

    @Override
    public IPaymentDao getPaymentDao() {
        return new PaymentDaoImpl(connection);
    }

    @Override
    public ITransactionDao getTransactionDao() {
        return new TransactionDaoImpl(connection);
    }
}