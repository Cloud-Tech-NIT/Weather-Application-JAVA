package Src.WeatherDataStorage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import Src.WeatherDataStorage.MySQLConnection;

public class DBAirPollution 
{
    public void insertWeatherData(float lat, float lon, String city, int dt, int aqi,
    float co,float no,float no2,float o3, float so2, float pm2_5,float pm10,float nh3)
     {
        int locationIdCounter = 0;
  String insertSql = "INSERT INTO Air_Pollution_Data (loc_id, city_name, latitude, longitude, dt, aqi, co, no, no2, o3, so2, pm2_5, pm10, nh3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = MySQLConnection.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                insertStatement.setInt(1, locationIdCounter);
                insertStatement.setString(2, city);                                                                                
                insertStatement.setDouble(3, lat);
                insertStatement.setDouble(4, lon);
                insertStatement.setLong(5, dt);
                insertStatement.setInt(6, aqi);
                insertStatement.setDouble(7, co);
                insertStatement.setDouble(8, no);
                insertStatement.setDouble(9, no2);
                insertStatement.setDouble(10, o3);
                insertStatement.setDouble(11, so2);
                insertStatement.setDouble(12, pm2_5);
                insertStatement.setDouble(13, pm10);
                insertStatement.setDouble(14, nh3);
                insertStatement.executeUpdate();
                 } catch (SQLException e)
         {
            e.printStackTrace();
        }
    }  
}
