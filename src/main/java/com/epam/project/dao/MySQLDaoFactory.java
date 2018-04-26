package com.epam.project.dao;

import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

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

    MySQLDaoFactory() throws IncorrectPropertyException {
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(new FileReader("src/main/java/dbConfig.properties"));
        } catch (IOException ioe) {
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
            throw new IncorrectPropertyException("Incorrect db property");
        } catch (NumberFormatException nfe) {
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

    public static Connection getConnection() throws DataBaseConnectionException {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException sqle) {
            throw new DataBaseConnectionException();
        }
    }

    public static void closeConnection(Connection connection) throws DataBaseConnectionException {
        try {
            connection.close();
        } catch (SQLException sqle) {
            throw new DataBaseConnectionException("Unable to close database connection");
        } catch (NullPointerException npe) {
            /** Nothing to close if null */
        }
    }

    @Override
    public IUserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public IProductDao getProductDao() {
        return new ProductDaoImpl();
    }

    @Override
    public IInvoiceDao getInvoiceDao() {
        return new InvoiceDaoImpl();
    }
}
