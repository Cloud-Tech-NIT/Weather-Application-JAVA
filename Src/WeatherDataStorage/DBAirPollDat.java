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
import Src.WeatherDataStorage.MySQLConnection;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.WeatherDataStorage.MySQLConnection;

public class DBAirPollDat 
{
    public DBAirPollDat() {
    }
    public void insertWeatherData(AirPollutionAPIData  jsonObject)
     {
         double lat =jsonObject.getLatitude(); 
            double lon = jsonObject.getLongitude();
            String city=jsonObject.getCityName();
            int dt =jsonObject.getDt(); 
            int aqi =jsonObject.getAqi();
            double co =jsonObject.getCo(); 
            double no = jsonObject.getNo();
            double no2 = jsonObject.getNo2();
            double o3 = jsonObject.getO3();
            double so2 = jsonObject.getSo2();
            double pm2_5 = jsonObject.getPm25();
            double pm10 = jsonObject.getPm10();
            double nh3 = jsonObject.getNh3();
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
                 } catch (SQLIntegrityConstraintViolationException e)
                {
                    // Handle duplicate primary key error (update existing record)
                    updateExistingRecord(jsonObject);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }  
    public void updateExistingRecord(AirPollutionAPIData  jsonObject)
    {
        double lat =jsonObject.getLatitude(); 
           double lon = jsonObject.getLongitude();
           String city=jsonObject.getCityName();
           int dt =jsonObject.getDt(); 
           int aqi =jsonObject.getAqi();
           double co =jsonObject.getCo(); 
           double no = jsonObject.getNo();
           double no2 = jsonObject.getNo2();
           double o3 = jsonObject.getO3();
           double so2 = jsonObject.getSo2();
           double pm2_5 = jsonObject.getPm25();
           double pm10 = jsonObject.getPm10();
           double nh3 = jsonObject.getNh3();
       int locationIdCounter = 0;

       String updateSql = "UPDATE Air_Pollution_Data SET city_name = ?, dt = ?, aqi = ?, co = ?, no = ?, no2 = ?, o3 = ?, so2 = ?, pm2_5 = ?, pm10 = ?, nh3 = ? WHERE latitude = ? AND longitude = ?";
           try (Connection connection = MySQLConnection.getConnection();
               PreparedStatement insertStatement = connection.prepareStatement(updateSql))
                {
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

               } catch (SQLException e) {
                   e.printStackTrace();
               }
   }  

    public boolean isDataPresentCoord(double latitude, double longitude) 
    {
        DBAirPollDat fetcher = new DBAirPollDat();
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM Air_Pollution_Data WHERE latitude = ? AND longitude = ?";
        try (Connection connection = MySQLConnection.getConnection())
         {
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
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
    public boolean isDataPresentByCityName(String cityName) {
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM air_pollution_data WHERE city_name = ?";
        try (Connection connection = MySQLConnection.getConnection())
        {
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
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
    public void displayDataFromDatabaseByCityName(String cityName) 
    {
        try (Connection connection = MySQLConnection.getConnection())
        {    String query = "SELECT * FROM Air_Pollution_Data WHERE city_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displayDataFromDatabaseByLatLon(double latitude, double longitude) {
        try(Connection connection = MySQLConnection.getConnection())
         {
            String query = "SELECT * FROM Air_Pollution_Data WHERE latitude = ? AND longitude = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayData(ResultSet resultSet) throws SQLException {
            while (resultSet.next())
             {
                int aqi = resultSet.getInt("aqi");
                double co = resultSet.getDouble("co");
                double no = resultSet.getDouble("no");
                double no2 = resultSet.getDouble("no2");
                double o3 = resultSet.getDouble("o3");
                double so2 = resultSet.getDouble("so2");
                double pm25 = resultSet.getDouble("pm2_5");
                double pm10 = resultSet.getDouble("pm10");
                double nh3 = resultSet.getDouble("nh3");

                System.out.println("Location ID: " + resultSet.getInt("loc_id"));
                System.out.println("City Name: " + resultSet.getString("city_name"));
                System.out.println("Latitude: " + resultSet.getDouble("latitude"));
                System.out.println("Longitude: " + resultSet.getDouble("longitude"));
                System.out.println("Date and Time: " + resultSet.getLong("dt"));
                System.out.println("AQI: " + aqi);
                System.out.println("CO: " + co);
                System.out.println("NO: " + no);
                System.out.println("NO2: " + no2);
                System.out.println("O3: " + o3);
                System.out.println("SO2: " + so2);
                System.out.println("PM2.5: " + pm25);
                System.out.println("PM10: " + pm10);
                System.out.println("NH3: " + nh3);
            }
    }
    public void deleteOldData() {
        try (Connection connection = MySQLConnection.getConnection()) {
            Instant sixHoursAgo = Instant.now().minus(Duration.ofHours(6));
            long sixHoursAgoEpoch = sixHoursAgo.getEpochSecond();
            String deleteSql = "DELETE FROM Air_Pollution_Data WHERE dt < ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setLong(1, sixHoursAgoEpoch);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBAirPollDat fetcher = new DBAirPollDat();

        // Schedule deletion of old data every 6 hours
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(fetcher::deleteOldData, 0, 6, TimeUnit.HOURS);
        // Message to check if the code runs properly
        System.out.println("Code executed successfully!");
    }
}

