package Src.WeatherDataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;
// import com.mysql.cj.xdevapi.PreparableStatement;

//this is a sample file to be used for all the different APIs

public class queryImplementSQL {

    public void createDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/";

            String databaseName = "weatherCache";
            String userName = "root";
            String password = "abc_123";

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

    public void createTable() {
        try {
            String url = "jdbc:mysql://localhost:3306/weatherCache";

            // String databaseName = "weatherCache";
            String userName = "root";
            String password = "abc_123";

            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement stm = connection.createStatement();
            // // alternate mechanism starts here

            String query = "create Table weatherDatastore(id INT PRIMARY KEY,location VARCHAR(255),timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,current_weather_data TEXT, forecast_data TEXT,air_pollution_data TEXT,INDEX(location),\r\n"
                    + //
                    "    INDEX(timestamp) )";
            stm.executeUpdate(query);
            System.out.println("Table Created Successfully!");
            // ////

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // insert values in the DB
    public void insertData() {
        try {
            String url = "jdbc:mysql://localhost:3306/weatherCache";

            // String databaseName = "weatherCache";
            String userName = "root";
            String password = "abc_123";

            Connection connection = DriverManager.getConnection(url, userName, password);
            // Statement stm = connection.createStatement();
            String query = "INSERT INTO  weatherDatastore(id ,location)  VALUES (?,?) )";

            PreparedStatement pstm = connection.prepareStatement(query);
            // assign values to the columns
            pstm.setInt(1, 2);
            pstm.setString(2, "location tester");
            /// continue for all parameters
            pstm.executeUpdate();
            // stm.executeUpdate(query);
            System.out.println("Values Inserted  Successfully!");
            // ////

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
