package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties props = new Properties();
                // Read local config.properties, not the example
                props.load(new FileInputStream("config/config.properties"));

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, pass);
            } catch (IOException e) {
                throw new SQLException("Error reading database config file", e);
            }
        }
        return connection;
    }
}

