package com.epam.project.dao;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;
import com.epam.project.exceptions.DataBaseConnectionException;
import com.epam.project.exceptions.IncorrectPropertyException;
import org.apache.commons.dbcp.BasicDataSource;

public class Connector {
    private static Connector connectionPool;
    private BasicDataSource dataSource;

    private Connector() throws IncorrectPropertyException {
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
        Integer maxOpenPStatements;
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
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxOpenPreparedStatements(maxOpenPStatements);
    }

    public static synchronized Connector getInstance() throws IncorrectPropertyException {
        if (connectionPool == null)
            connectionPool = new Connector();
        return connectionPool;
    }

    public Connection getConnection() throws DataBaseConnectionException {
        try {
            return this.dataSource.getConnection();
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
}