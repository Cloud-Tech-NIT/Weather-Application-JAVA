package Src.WeatherDataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class createDB {

    public static void main(String[] args) throws Exception {
        try {
            String url = "jdbc:mysql://localhost:3306/";

            String databaseName = "weather_Cache";
            String userName = "root";
            String password = "4820";

            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement stm = connection.createStatement();

            String sql = "CREATE DATABASE " + databaseName;

            Statement statement = connection.createStatement();
            // // alternate mechanism starts here
            // String query = "create database weatherApp";
            // boolean bl = statement.execute(query);
            // System.out.println("Databse created Successfully!" + bl);
            // ////
            statement.executeUpdate(sql);
            statement.close();
            JOptionPane.showMessageDialog(null, databaseName + " Database has been created successfully",
                    "System Message", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}