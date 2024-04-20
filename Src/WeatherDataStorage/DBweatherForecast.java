package Src.WeatherDataStorage;

import Src.AppUI.mainscreenController;
import java.io.BufferedReader;
import java.io.IOException;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DBweatherForecast {
    public DBweatherForecast() {
    }

    private mainscreenController controller;

    // Method to set the mainscreenController reference
    public void setController(mainscreenController controller) {
        this.controller = controller;
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/weather_cache";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "4820";
    private static final String API_KEY = "cc7d0c84d9aca07ad0bc1494b2af27a0";
    private static final String UNITS = "metric";

    private int locationIdCounter = 0;

    public void parseJSON(JsonObject jsonObject) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            // Query to get the last location ID
            String lastLocationIdQuery = "SELECT MAX(Day) FROM weather_Forecast";
            PreparedStatement lastLocationIdStatement = connection.prepareStatement(lastLocationIdQuery);
            ResultSet lastLocationIdResult = lastLocationIdStatement.executeQuery();
            int lastDay = 0;
            if (lastLocationIdResult.next()) {
                lastDay = lastLocationIdResult.getInt(1);
            }
            int dayIndex = lastDay + 1; // Assigning the new day index

            JsonObject coord = jsonObject.getAsJsonObject("coord");
            double lat = coord.get("lat").getAsDouble();
            double lon = coord.get("lon").getAsDouble();
            String cityName = jsonObject.get("name").getAsString();

            // Data parsed and get
            JsonArray list = jsonObject.getAsJsonArray("list");

            // Prepare insert statement
            String insertSql = "INSERT INTO weather_Forecast (Day, city_name, latitude, longitude, temp, temp_min, temp_max, pressure, humidity, icon_url, weather_condition) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            // Iterate through each day's data
            for (int i = 0; i < list.size(); i++) {
                JsonObject item = list.get(i).getAsJsonObject();
                JsonObject main = item.getAsJsonObject("main");

                double temp = main.get("temp").getAsDouble();
                double temp_min = main.get("temp_min").getAsDouble();
                double temp_max = main.get("temp_max").getAsDouble();
                double pressure = main.get("pressure").getAsDouble();
                double humidity = main.get("humidity").getAsDouble();

                JsonArray weatherArray = item.getAsJsonArray("weather");
                JsonObject weather = weatherArray.get(0).getAsJsonObject();
                String iconCode = weather.get("icon").getAsString(); // Icon of current weather
                String weatherMain = weather.get("main").getAsString();

                String baseIconUrl = "https://openweathermap.org/img/wn/"; // url for the icons
                String iconUrl = baseIconUrl + iconCode + "@2x.png"; // final url for icon

                // Set values for the insert statement
                preparedStatement.setInt(1, dayIndex);
                preparedStatement.setString(2, cityName);
                preparedStatement.setDouble(3, lat);
                preparedStatement.setDouble(4, lon);
                preparedStatement.setDouble(5, temp);
                preparedStatement.setDouble(6, temp_min);
                preparedStatement.setDouble(7, temp_max);
                preparedStatement.setDouble(8, pressure);
                preparedStatement.setDouble(9, humidity);
                preparedStatement.setString(10, iconUrl);
                preparedStatement.setString(11, weatherMain);

                // Add data for the current day
                preparedStatement.executeUpdate();

                // Move to the next day
                dayIndex++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Function to check if data already present in database by city name

    public boolean isDataPresentByCityName(Connection connection, String cityName) {
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

    // Function to check if data already present in database by latitude and
    // longitude
    public boolean isDataPresentByLatLon(Connection connection, double latitude, double longitude) {
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

    ////////
    // Function to display fetched data from the database by city name
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

    // Function to display fetched data from the database by latitude and longitude
    public void displayDataFromDatabaseByLatLon(Connection connection, double latitude, double longitude) {
        try {
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
        try {
            while (resultSet.next()) {
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

                System.out.println("Day: " + day);
                System.out.println("City Name: " + cityName);
                System.out.println("Latitude: " + latitude);
                System.out.println("Longitude: " + longitude);
                System.out.println("Temperature: " + temp);
                System.out.println("Minimum Temperature: " + temp_min);
                System.out.println("Maximum Temperature: " + temp_max);
                System.out.println("Pressure: " + pressure);
                System.out.println("Humidity: " + humidity);
                System.out.println("Icon URL: " + iconUrl);
                System.out.println("Weather Condition: " + weatherCondition);
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete data in table after 6 hours
    public void deleteOldData() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
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
    ////////////////

    public void APIcall(String cityName) {
        try {
            @SuppressWarnings("deprecation")
            URL apiUrl = new URL(
                    "https://api.openweathermap.org/data/2.5/weather?q=" +
                            cityName +
                            "&appid=" +
                            API_KEY);
            performAPICall(apiUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performAPICall(URL apiUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(
                response.toString(),
                JsonObject.class);
        parseJSON(jsonObject);
        System.out.println(jsonObject);
        connection.disconnect();
    }

    public void APIcall(double latitude, double longitude) {
        try {
            @SuppressWarnings("deprecation")
            // Create URL with latitude, longitude, and API key
            URL url = new URL(
                    "https://api.openweathermap.org/data/2.5/weather?lat=" +
                            latitude +
                            "&lon=" +
                            longitude +
                            "&appid=" +
                            API_KEY +
                            "&units=" +
                            UNITS);
            performAPICall(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBweatherForecast fetcher = new DBweatherForecast();

        // Schedule deletion of old data every 6 hours
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(fetcher::deleteOldData, 0, 6, TimeUnit.HOURS);

        // Example API call
        fetcher.APIcall(40.7128, -74.0060); // Example latitude and longitude
    }
}