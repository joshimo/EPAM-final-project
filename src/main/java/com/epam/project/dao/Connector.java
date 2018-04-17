package com.epam.project.dao;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class Connector {
    private static Connector connectionPool;
    private BasicDataSource dataSource;

    private Connector() throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(new FileReader("src/main/java/dbConfig.properties"));
        String user = dbProperties.getProperty("user");
        String password = dbProperties.getProperty("password");
        String host = dbProperties.getProperty("host");
        String port = dbProperties.getProperty("port");
        String database = dbProperties.getProperty("database");
        String useUnicode = dbProperties.getProperty("useUnicode");
        String encoding = dbProperties.getProperty("encoding");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=" + useUnicode + "&characterEncoding=" + encoding;
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setMinIdle(8);
        dataSource.setMaxIdle(16);
        dataSource.setMaxOpenPreparedStatements(180);
    }

    public static Connector getInstance() throws IOException, SQLException {
        if (connectionPool == null)
            connectionPool = new Connector();
        return connectionPool;
    }

    public java.sql.Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    static ResultSet sendRequest(Statement statement, String query) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            closeStatement(statement);
        }
        return resultSet;
    }

    static void closeStatement(Statement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException sqle1) {
            sqle1.printStackTrace();
        }
    }

    public static void closeConnection(java.sql.Connection connection) {
        try {
            connection.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void info() {
        System.out.println("Max. Active connections: " + this.dataSource.getMaxActive());
        System.out.println("Active connections: " + this.dataSource.getNumActive());
        System.out.println("Max. Idle connections: " + this.dataSource.getMaxIdle());
        System.out.println("Idle connections: " + this.dataSource.getNumIdle());
    }
}
