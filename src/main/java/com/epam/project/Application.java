package com.epam.project;

import com.epam.project.dao.Connector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {

    public static void main(String... args) throws SQLException, IOException {
        Connection connection = Connector.createConnection();
        System.out.println(!connection.isClosed());
        connection.close();
    }
}
