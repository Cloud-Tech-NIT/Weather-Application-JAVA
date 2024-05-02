package Src.WeatherDataStorage.jdbcDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/weather_cache";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "4820";

    // Method to establish a connection to the MySQL database
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Registering the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establishing the connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to connect to the database.");
        }
        return connection;
    }

    // Main method for testing the connection
    public static void main(String[] args) {
        try {
            // Getting a connection object
            Connection connection = MySQLConnection.getConnection();
            if (connection != null) {
                System.out.println("Connection successful!");
                connection.close();
            } else {
                System.out.println("Failed to establish connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
