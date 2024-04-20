package Src.WeatherDataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;
import com.mysql.cj.xdevapi.PreparableStatement;

//File Desc: This file creates creation of seperate table for storing the data fetched from each API

public class createTables {
    // create table to store data of Current Weather Conditions API

    public void Table_CurrentWeatherData() {
        try {
            String url = "jdbc:mysql://localhost:3306/weather_cache";
            String userName = "root";
            String password = "4820";

            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement stm = connection.createStatement();

            // sql query for creating tables
            // **If you want to change data type of any variable, change in this */

            String query = "CREATE TABLE Current_Weather_Data(loc_id INT,city_name VARCHAR(255) PRIMARY KEY,latitude Double,longitude Double,weather_main VARCHAR(255), weather_description VARCHAR(255),weather_icon VARCHAR(255),temperature Float,feels_like Float,temp_min Float,temp_max Float,pressure INT,humidity INT,visibility INT,wind_speed Float,rain Float,clouds_all INT,dt INT,country VARCHAR(255),sunrise INT, sunset INT,timezone INT)";

            stm.executeUpdate(query);
            System.out.println(" Current_Weather_Data Table Created Successfully!");
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Table_Air_Pollution() {
        try {
            String url = "jdbc:mysql://localhost:3306/weather_cache";
            String userName = "root";
            String password = "4820";

            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement stm = connection.createStatement();

            // sql query for creating tables
            // **If you want to change data type of any variable, change in this */

            String query = "CREATE TABLE Air_Pollution_Data(loc_id INT ,city_name VARCHAR(255),latitude Double,longitude Double,dt INT,aqi INT,co FLOAT,no FLOAT,no2 FLOAT,o3 FLOAT, so2 FLOAT,pm2_5 FLOAT,pm10 FLOAT,nh3 FLOAT, PRIMARY KEY(latitude,longitude))";
            stm.executeUpdate(query);
            System.out.println("Air Pollution Table Created Successfully!");
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Table_weather_Forecast() {
        try {
            String url = "jdbc:mysql://localhost:3306/weather_cache";
            String userName = "root";
            String password = "4820";

            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement stm = connection.createStatement();

            // sql query for creating tables
            // **If you want to change data type of any variable, change in this */

            String query = "CREATE TABLE weather_Forecast (Day INT,city_name VARCHAR(255) ,latitude Double, longitude Double,temp FLOAT,temp_min FLOAT, temp_max FLOAT,pressure FLOAT,humidity FLOAT,PRIMARY KEY (Day, city_name))";
            stm.executeUpdate(query);
            System.out.println("weather_Forecast Table Created Successfully!");
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
