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
import Src.WeatherDataStorage.MySQLConnection;


public class DBCurrWeather 
{
    private MySQLConnection Connection=new MySQLConnection();
    
    private int locationIdCounter = 0;

    public void insertWeatherData(CurrentWeatherAPIData  jsonObject)
     {
        try (Connection connection = MySQLConnection.getConnection()) 
        {
            // Query to get the last location ID
            String lastLocationIdQuery = "SELECT MAX(loc_id) FROM Current_Weather_Data";
            PreparedStatement lastLocationIdStatement = connection.prepareStatement(lastLocationIdQuery);
            ResultSet lastLocationIdResult = lastLocationIdStatement.executeQuery();
            int lastLocationId = 0;
            if (lastLocationIdResult.next()) {
                lastLocationId = lastLocationIdResult.getInt(1);
            }
            int locationIdCounter = lastLocationId + 1; // Assigning the new location ID

            float lat = jsonObject.getLatitude();
            float lon = jsonObject.getLongitude();
            String cityName = jsonObject.getCityName();// Changed variable name to cityName
            String weatherMain = jsonObject.getWeatherMain();
            String weatherDescription = jsonObject.getWeatherDescription();
            String weatherIcon = jsonObject.getWeatherIcon(); // Icon of current weather
            float temp = jsonObject.getTemperature();
            float feelsLike =jsonObject.getFeelsLike(); 
            float tempMin =jsonObject.getTempMin(); 
            float tempMax =jsonObject.getTempMax(); 
            int pressure = jsonObject.getPressure();
            int humidity = jsonObject.getHumidity();
            int visibility = jsonObject.getVisibility();
            float windSpeed = jsonObject.getWindSpeed();
            double rainVolume = jsonObject.getRain(); // Assume rain volume is in "1h" key
            int cloudsAll =jsonObject.getCloudsAll();
            int dt = jsonObject.getDt(); // Time of data calculation
            String country = jsonObject.getCountry();
            int sunrise = jsonObject.getSunrise(); // Sunrise Time
            int sunset = jsonObject.getSunset();
            int timezone = jsonObject.getTimezone();
            // Store data in the database
            String insertSql = "INSERT INTO Current_Weather_Data (loc_id, city_name, latitude, longitude, weather_main, weather_description, weather_icon, temperature, feels_like, temp_min, temp_max, pressure, humidity, visibility, wind_speed, rain, clouds_all, dt, country, sunrise, sunset, timezone) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setInt(1, locationIdCounter);
                preparedStatement.setString(2, cityName);
                preparedStatement.setDouble(3, lat);
                preparedStatement.setDouble(4, lon);
                preparedStatement.setString(5, weatherMain);
                preparedStatement.setString(6, weatherDescription);
                preparedStatement.setString(7, weatherIcon);
                preparedStatement.setDouble(8, temp);
                preparedStatement.setDouble(9, feelsLike);
                preparedStatement.setDouble(10, tempMin);
                preparedStatement.setDouble(11, tempMax);
                preparedStatement.setInt(12, pressure);
                preparedStatement.setInt(13, humidity);
                preparedStatement.setInt(14, visibility);
                preparedStatement.setDouble(15, windSpeed);
                preparedStatement.setDouble(16, rainVolume);
                preparedStatement.setInt(17, cloudsAll);
                preparedStatement.setInt(18, dt);
                preparedStatement.setString(19, country);
                preparedStatement.setInt(20, sunrise);
                preparedStatement.setInt(21, sunset);
                preparedStatement.setInt(22, timezone);

                // Execute the SQL statement
                preparedStatement.executeUpdate();

                System.out.println("Data inserted successfully!"); // Only a check to see if data inserted or not
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isDataPresentByCityName(String cityName) {
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM Current_Weather_Data WHERE city_name = ?";
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
    public boolean isDataPresentByLatLon(double latitude, double longitude) {
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM Current_Weather_Data WHERE latitude = ? AND longitude = ?";
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
    public void displayDataFromDatabaseByLatLon(double latitude, double longitude) {
        try(Connection connection = MySQLConnection.getConnection())
         {
            String query = "SELECT * FROM Current_Weather_Data WHERE latitude = ? AND longitude = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displayDataFromDatabaseByCityName(String cityName) 
    {
        try (Connection connection = MySQLConnection.getConnection())
        {    String query = "SELECT * FROM Current_Weather_Data WHERE city_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Helper method to display fetched data from ResultSet
    private void displayData(ResultSet resultSet) throws SQLException {
        while (resultSet.next())
         {
            // Extract data from ResultSet and display it
            int loc_id = resultSet.getInt("loc_id");
            String city_name = resultSet.getString("city_name");
            double lat = resultSet.getDouble("latitude");
            double lon = resultSet.getDouble("longitude");
            String weather_main = resultSet.getString("weather_main");
            String weather_description = resultSet.getString("weather_description");
            String weather_icon = resultSet.getString("weather_icon");
            double temperature = resultSet.getDouble("temperature");
            double feels_like = resultSet.getDouble("feels_like");
            double temp_min = resultSet.getDouble("temp_min");
            double temp_max = resultSet.getDouble("temp_max");
            int pressure = resultSet.getInt("pressure");
            int humidity = resultSet.getInt("humidity");
            int visibility = resultSet.getInt("visibility");
            double wind_speed = resultSet.getDouble("wind_speed");
            double rain = resultSet.getDouble("rain");
            int clouds_all = resultSet.getInt("clouds_all");
            int dt = resultSet.getInt("dt");
            String country = resultSet.getString("country");
            int sunrise = resultSet.getInt("sunrise");
            int sunset = resultSet.getInt("sunset");
            int timezone = resultSet.getInt("timezone");
            String baseIconUrl = "https://openweathermap.org/img/wn/"; // url for the icons
            String iconUrl = baseIconUrl + weather_icon + "@2x.png"; // final url for icon
            // controller.updateUI(controller, city_name, country, temperature + 273, weather_main, iconUrl, lat, lon,
            //         feels_like + 273,
            //         humidity, temp_min + 273, temp_max + 273, sunrise, sunset, pressure, wind_speed, timezone);

            // Display the fetched data
            System.out.println("Location ID: " + loc_id);
            System.out.println("City Name: " + city_name);
            System.out.println("Latitude: " + lat);
            System.out.println("Longitude: " + lon);
            System.out.println("Weather Main: " + weather_main);
            System.out.println("Weather Description: " + weather_description);
            System.out.println("Weather Icon: " + weather_icon);
            System.out.println("Temperature: " + temperature);
            System.out.println("Feels Like: " + feels_like);
            System.out.println("Temp Min: " + temp_min);
            System.out.println("Temp Max: " + temp_max);
            System.out.println("Pressure: " + pressure);
            System.out.println("Humidity: " + humidity);
            System.out.println("Visibility: " + visibility);
            System.out.println("Wind Speed: " + wind_speed);
            System.out.println("Rain: " + rain);
            System.out.println("Clouds All: " + clouds_all);
            System.out.println("DT: " + dt);
            System.out.println("Country: " + country);
            System.out.println("Sunrise: " + sunrise);
            System.out.println("Sunset: " + sunset);
            System.out.println("Timezone: " + timezone);
            System.out.println();
        }
    }

    // Delete data in table after 6 hours
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
    public static void main(String[] args) {
        DBCurrWeather fetcher = new DBCurrWeather();

        // Schedule deletion of old data every 6 hours
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(fetcher::deleteOldData, 0, 6, TimeUnit.HOURS);
        System.err.println("Insertion Sucessful");
    }

}
