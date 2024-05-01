package Src.WeatherDataStorage.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;
import com.mysql.cj.xdevapi.PreparableStatement;

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

            String query = "CREATE TABLE current_weather_data(loc_id INT ,city_name VARCHAR(255) PRIMARY KEY,latitude double,longitude double,weather_main VARCHAR(255), weather_description VARCHAR(255),weather_icon VARCHAR(255),temperature Float,feels_like Float,temp_min Float,temp_max Float,pressure INT,humidity INT,visibility INT,wind_speed Float,rain Float,clouds_all INT,dt INT,country VARCHAR(255),sunrise INT, sunset INT,timezone INT)";

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

            String query = "CREATE TABLE air_pollution_data(loc_id INT,city_name VARCHAR(255),latitude double,longitude double,dt INT,aqi INT,co FLOAT,no FLOAT,no2 FLOAT,o3 FLOAT, so2 FLOAT,pm2_5 FLOAT,pm10 FLOAT,nh3 FLOAT,PRIMARY KEY (latitude, longitude) )";
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

            String query = "CREATE TABLE weather_forecast (day INT,city_name VARCHAR(255),latitude double,longitude double,temp FLOAT,temp_min FLOAT, temp_max FLOAT,pressure FLOAT,humidity FLOAT,weather_condition VARCHAR(255), icon_url VARCHAR(255),PRIMARY KEY (day, city_name))";
            stm.executeUpdate(query);
            System.out.println("weather_Forecast Table Created Successfully!");
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createTables tablesCreator = new createTables();

        // Create tables for current weather data
        tablesCreator.Table_CurrentWeatherData();

        // Create tables for air pollution data
        tablesCreator.Table_Air_Pollution();

        // Create tables for weather forecast data
        tablesCreator.Table_weather_Forecast();
    }
}
