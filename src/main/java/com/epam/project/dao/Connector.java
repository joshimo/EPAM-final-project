package com.epam.project.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=" + useUnicode + "&encoding=" + encoding;
        return DriverManager.getConnection(url, user, password);
    }
}
