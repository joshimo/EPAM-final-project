package com.epam.project.dao;

import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.function.Function;

public class MySQLDaoFactory extends DaoFactory {

    private String user;
    private String password;
    private String host;
    private String port;
    private String database;
    private String useUnicode;
    private String encoding;
    private String url;
    private Integer minIdle;
    private Integer maxIdle;
    private Integer maxOpenPStatements;
    private static BasicDataSource basicDataSource;

    private static final Logger log = Logger.getLogger(MySQLDaoFactory.class);
    private Connection connection;

    MySQLDaoFactory() throws IncorrectPropertyException, DataBaseConnectionException {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(new FileReader("src/main/java/dbConfig.properties"));
        } catch (IOException ioe) {
            log.error("Database property file not found");
            throw new IncorrectPropertyException("Database property file not found");
        }
        try {
            user = dbProperties.getProperty("user");
            password = dbProperties.getProperty("password");
            host = dbProperties.getProperty("host");
            port = dbProperties.getProperty("port");
            database = dbProperties.getProperty("database");
            useUnicode = dbProperties.getProperty("useUnicode");
            encoding = dbProperties.getProperty("encoding");
            url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=" + useUnicode + "&characterEncoding=" + encoding;
            minIdle = Integer.parseInt(dbProperties.getProperty("minIdle"));
            maxIdle = Integer.parseInt(dbProperties.getProperty("maxIdle"));
            maxOpenPStatements = Integer.parseInt(dbProperties.getProperty("maxOpenPreparedStatements"));
        } catch (NullPointerException npe) {
            log.error("Incorrect db property");
            throw new IncorrectPropertyException("Incorrect db property");
        } catch (NumberFormatException nfe) {
            log.error("Incorrect db property");
            throw new IncorrectPropertyException("Incorrect db property");
        }
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setMinIdle(minIdle);
        basicDataSource.setMaxIdle(maxIdle);
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