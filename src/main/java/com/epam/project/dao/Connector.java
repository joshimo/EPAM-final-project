package com.epam.project.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Connector {
    public static Connection createConnection() throws SQLException, IOException {
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
        return DriverManager.getConnection(url, user, password);
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

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
