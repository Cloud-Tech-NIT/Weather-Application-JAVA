package Src.WeatherDataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.WeatherDataStorage.MySQLConnection;


public class DBweatherFrcst {
   private MySQLConnection Connection=new MySQLConnection();
    private WeatherForecastAPIData jobj= new CurrentWeatherAPIData();

    public void insertWeatherData(WeatherForecastAPIData jsonObject) {
        try (Connection connection = MySQLConnection.getConnection()) {
            int dayIndex = 0;
            float lat = jsonObject.getLatitude();
            float lon = jsonObject.getLongitude();
            String cityName = jsonObject.getCityName();
            double[][] data = jsonObject.getData();
            String[] iconUrls = jsonObject.getIconUrls();
            String[] weatherConditions = jsonObject.getWeatherCondition();
    
            String insertSql = "INSERT INTO weather_forecast (Day, city_name, latitude, longitude, temp, temp_min, temp_max, pressure, humidity, weather_condition, icon_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                for (int i = 0; i < 5; i++) {
                    preparedStatement.setInt(1, dayIndex);
                    preparedStatement.setString(2, cityName);
                    preparedStatement.setDouble(3, lat);
                    preparedStatement.setDouble(4, lon);
                    preparedStatement.setDouble(5, data[i][0]); // Assuming data[i][0] is the temperature
                    preparedStatement.setDouble(6, data[i][1]); // Assuming data[i][1] is the minimum temperature
                    preparedStatement.setDouble(7, data[i][2]); // Assuming data[i][2] is the maximum temperature
                    preparedStatement.setDouble(8, data[i][3]); // Assuming data[i][3] is the pressure
                    preparedStatement.setDouble(9, data[i][4]); // Assuming data[i][4] is the humidity
                    preparedStatement.setString(10, weatherConditions[i]);
                    preparedStatement.setString(11, iconUrls[i]);
                    preparedStatement.executeUpdate();
                    dayIndex++;
                }
    
                System.out.println("Data inserted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDataPresentByCityName(Connection connection, String cityName) {
        DBweatherForecast fetcher = new DBweatherForecast();
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM weather_Forecast WHERE city_name = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setString(1, cityName);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                present = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return present;
    }
    public boolean isDataPresentByLatLon(Connection connection, double latitude, double longitude) {
        DBweatherForecast fetcher = new DBweatherForecast();
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM weather_Forecast WHERE latitude = ? AND longitude = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setDouble(1, latitude);
            selectStatement.setDouble(2, longitude);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                present = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return present;
    }
    public void displayDataFromDatabaseByCityName(Connection connection, String cityName) {
        try {
            String query = "SELECT * FROM weather_Forecast WHERE city_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayDataAsArray(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displayDataFromDatabaseByLatLon(Connection connection, double latitude, double longitude) {
        try (Connection connection = MySQLConnection.getConnection())
        {
            String query = "SELECT * FROM weather_Forecast WHERE latitude = ? AND longitude = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayDataAsArray(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     // Function to display fetched data as array
     public void displayDataAsArray(ResultSet resultSet) {
        try (Connection connection = MySQLConnection.getConnection())
        {
            while (resultSet.next()) {
                 for(int i=0;i<5;i++)
                 {
                    int day = resultSet.getInt("Day");
                    String cityName = resultSet.getString("city_name");
                    double latitude = resultSet.getDouble("latitude");
                    double longitude = resultSet.getDouble("longitude");
                    double temp = resultSet.getDouble("temp");
                    double temp_min = resultSet.getDouble("temp_min");
                    double temp_max = resultSet.getDouble("temp_max");
                    double pressure = resultSet.getDouble("pressure");
                    double humidity = resultSet.getDouble("humidity");
                    String iconUrl = resultSet.getString("icon_url");
                    String weatherCondition = resultSet.getString("weather_condition");

                
                 }
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteOldData() {
        try (Connection connection = MySQLConnection.getConnection()) 
        {
            Instant sixHoursAgo = Instant.now().minus(Duration.ofHours(6));
            long sixHoursAgoEpoch = sixHoursAgo.getEpochSecond();
            String deleteSql = "DELETE FROM Current_Weather_Data WHERE dt < ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setLong(1, sixHoursAgoEpoch);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args)
    {
        DBweatherFrcst fetcher = new DBweatherFrcst();

        // Schedule deletion of old data every 6 hours
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(fetcher::deleteOldData, 0, 6, TimeUnit.HOURS);
    }

}



       
    
