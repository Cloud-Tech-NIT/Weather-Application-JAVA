import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DBAirPoll {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/weather_Cache";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "abc_123";
    private static final String API_KEY = "cc7d0c84d9aca07ad0bc1494b2af27a0";
    private static final String UNITS = "metric"; // or "imperial" depending on your preference

    private int locationIdCounter = 0;

    public void parseJSON(JsonObject jsonObject) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            JsonObject coordObject = jsonObject.getAsJsonObject("coord");
            double lat = coordObject.get("lat").getAsDouble();
            double lon = coordObject.get("lon").getAsDouble();
            
            // Check if data is already present in the database
            if (isDataPresent(connection, lat, lon)) {
                displayDataFromDatabase(connection, lat, lon);
                return;
            }
            
            JsonArray listArray = jsonObject.getAsJsonArray("list");
            JsonObject firstItem = listArray.get(0).getAsJsonObject();
            long dt = firstItem.get("dt").getAsLong();
            JsonObject main = firstItem.getAsJsonObject("main");
            int aqi = main.get("aqi").getAsInt();
            JsonObject components = firstItem.getAsJsonObject("components");
            double co = components.get("co").getAsDouble();
            double no = components.get("no").getAsDouble();
            double no2 = components.get("no2").getAsDouble();
            double o3 = components.get("o3").getAsDouble();
            double so2 = components.get("so2").getAsDouble();
            double pm2_5 = components.get("pm2_5").getAsDouble();
            double pm10 = components.get("pm10").getAsDouble();
            double nh3 = components.get("nh3").getAsDouble();

            // Increment and assign location ID
            locationIdCounter++;

            // Store data in the database
            String insertSql = "INSERT INTO Air_Pollution_Data (loc_id, city_name, latitude, longitude, dt, aqi, co, no, no2, o3, so2, pm2_5, pm10, nh3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                insertStatement.setInt(1, locationIdCounter);
                insertStatement.setString(2, getCityNameFromCurrentWeatherData(connection, lat, lon)); // Obtain city name from current weather data table
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isDataPresent(Connection connection, double latitude, double longitude) {
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM Air_Pollution_Data WHERE latitude = ? AND longitude = ?";
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

    private void displayDataFromDatabase(Connection connection, double latitude, double longitude) {
        String selectSql = "SELECT * FROM Air_Pollution_Data WHERE latitude = ? AND longitude = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setDouble(1, latitude);
            selectStatement.setDouble(2, longitude);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Location ID: " + resultSet.getInt("loc_id"));
                System.out.println("City Name: " + resultSet.getString("city_name"));
                System.out.println("Latitude: " + resultSet.getDouble("latitude"));
                System.out.println("Longitude: " + resultSet.getDouble("longitude"));
                System.out.println("Date and Time: " + resultSet.getLong("dt"));
                System.out.println("AQI: " + resultSet.getInt("aqi"));
                System.out.println("CO: " + resultSet.getDouble("co"));
                System.out.println("NO: " + resultSet.getDouble("no"));
                System.out.println("NO2: " + resultSet.getDouble("no2"));
                System.out.println("O3: " + resultSet.getDouble("o3"));
                System.out.println("SO2: " + resultSet.getDouble("so2"));
                System.out.println("PM2.5: " + resultSet.getDouble("pm2_5"));
                System.out.println("PM10: " + resultSet.getDouble("pm10"));
                System.out.println("NH3: " + resultSet.getDouble("nh3"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCityNameFromCurrentWeatherData(Connection connection, double latitude, double longitude) throws SQLException {
        String cityName = "Unknown";
        String selectSql = "SELECT city_name FROM Current_Weather_Data WHERE latitude = ? AND longitude = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setDouble(1, latitude);
            selectStatement.setDouble(2, longitude);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                cityName = resultSet.getString("city_name");
            }
        }
        return cityName;
    }

    public void deleteOldData() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
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

    public void APIcall(double latitude, double longitude) {
        try {
            // Delete old data
            deleteOldData();

            // Proceed with API call
            String apiUrl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" +
            latitude +
            "&lon=" +
            longitude +
            "&appid=" +
            API_KEY +
            "&units=" +
            UNITS;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(response.toString()).getAsJsonObject();
                parseJSON(jsonObject);
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
public static void main(String[] args) {
    DBAirPoll fetcher = new DBAirPoll();

    // Schedule deletion of old data every 6 hours
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(fetcher::deleteOldData, 0, 6, TimeUnit.HOURS);

    // Example API call
    fetcher.APIcall(40.7128, -74.0060); // Example latitude and longitude
}




