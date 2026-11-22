
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection getConnection()
            throws SQLException, IOException {

        Properties properties = new Properties();

        try (FileInputStream file =
                new FileInputStream("db.properties")) {

            properties.load(file);
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(
            url, username, password
        );
    }
}
