package Src.WeatherDataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mainTest {
    String url = "jdbc:mysql://localhost:3306/weatherCache";
    // db credentials
    static String DB_URL = "weatherCache";
    static String DB_USER = "root";
    static String DB_PASSWORD = "4820";

    public static void main(String[] args) {
        queryImplementSQL data = new queryImplementSQL();
        // data.createDatabase(); //creates the database where data will be stored in
        // sql
        // data.createTable(); //creates a table within the database of the curr date

        data.insertData(); // insert data into dB

    }

    // condition to check if weather from api or db
    public static String getWeatherData(double lat, double lon) {
        String location = lat + "," + lon;
        String cachedData = getCachedWeatherData(location);

        if (cachedData != null && !isCacheExpired(location)) {
            return cachedData;
        } else {
            // Fetch data from API and update cache
            String weatherData = fetchWeatherDataFromAPI(lat, lon);
            updateWeatherCache(location, weatherData);
            return weatherData;
        }
    }

    // fetch weather data from API
    private static String fetchWeatherDataFromAPI(double lat, double lon) {
        // call OpenWeatherMap APIs and fetch weather data
        // parse json files
        return "weather data"; // Replace with actual API call
    }

    // delete cache after 4 hrs
    private static boolean isCacheExpired(String location) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT last_updated FROM WeatherCache WHERE location = ?")) {
            stmt.setString(1, location);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp lastUpdated = rs.getTimestamp("last_updated");
                // Assuming cache expires after 24 hours
                return lastUpdated.before(new Timestamp(System.currentTimeMillis() - (4 * 60 * 60 * 1000)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // Return true if cache doesn't exist or unable to check expiration
    }

    // get weather data from the database
    private static String getCachedWeatherData(String location) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT weather_data FROM WeatherCache WHERE location = ?")) {
            stmt.setString(1, location);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("weather_data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if cache doesn't exist
    }

    // update DB
    private static void updateWeatherCache(String location, String weatherData) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn
                        .prepareStatement("INSERT INTO WeatherCache (location, weather_data) VALUES (?, ?) " +
                                "ON DUPLICATE KEY UPDATE weather_data = VALUES(weather_data), last_updated = CURRENT_TIMESTAMP")) {
            stmt.setString(1, location);
            stmt.setString(2, weatherData);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
