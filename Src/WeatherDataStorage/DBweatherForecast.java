package Src.WeatherDataStorage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DBweatherForecast {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/weather_Cache";
    private static final String USER = "root";
    private static final String PASSWORD = "abc_123";
    private static final String APIkey = "cc7d0c84d9aca07ad0bc1494b2af27a0"; 
    private static final String units = "metric"; // Units for temperature

    // JDBC variables for opening, closing and managing connection
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    // Method to establish a database connection
    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to close the database connection
    public static void close() throws SQLException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to check if weather data exists for given latitude and longitude or city
    public static boolean weatherDataExists(double latitude, double longitude, String city) throws SQLException {
        String checkDataSQL = "SELECT COUNT(*) FROM weather_forecast WHERE (latitude = ? AND longitude = ?) OR city=?";
        preparedStatement = connection.prepareStatement(checkDataSQL);
        preparedStatement.setString(1, city);
        preparedStatement.setDouble(2, latitude);
        preparedStatement.setDouble(3, longitude);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    }

    // Method to fetch weather data from the database
    public static void fetchWeatherData(double latitude, double longitude, String city) throws SQLException {
        String fetchDataSQL = "SELECT * FROM weather_forecast WHERE latitude = ? AND longitude = ?";
        preparedStatement = connection.prepareStatement(fetchDataSQL);
        preparedStatement.setDouble(1, latitude);
        preparedStatement.setDouble(2, longitude);
        resultSet = preparedStatement.executeQuery();
        // Display fetched data
        // Your display logic here
    }

    // Method to insert parsed data into the database
    public static void insertWeatherData(double[][] data, String[] iconUrls, String[] weatherConditions, double latitude, double longitude, String city) throws SQLException {
        String insertSQL = "INSERT INTO weather_forecast (Day, city, latitude, longitude, temp, temp_min, temp_max, pressure, humidity, icon_url, weather_condition) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(insertSQL);

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, city);
                preparedStatement.setDouble(3, latitude);
                preparedStatement.setDouble(4, longitude);
                preparedStatement.setDouble(5, data[i][0]);
                preparedStatement.setDouble(6, data[i][1]);
                preparedStatement.setDouble(7, data[i][2]);
                preparedStatement.setDouble(8, data[i][3]);
                preparedStatement.setDouble(9, data[i][4]);
                preparedStatement.setString(10, iconUrls[i]);
                preparedStatement.setString(11, weatherConditions[i]);
                preparedStatement.addBatch();
            }
        }
        preparedStatement.executeBatch();
    }

    // Method to parse JSON and store data in the database
    public void parseJSON(JsonObject jsonObject, double latitude, double longitude, String city) {
        try {
            connect();

            // Extract latitude, longitude, and city from the JSON object
            latitude = jsonObject.getAsJsonObject("city").getAsJsonObject("coord").get("lat").getAsDouble();
            longitude = jsonObject.getAsJsonObject("city").getAsJsonObject("coord").get("lon").getAsDouble();
            city = jsonObject.getAsJsonObject("city").get("name").getAsString();

            // Check if weather data exists in the database
            if (weatherDataExists(latitude, longitude, city)) {
                fetchWeatherData(latitude, longitude, city);
            } else {
                // Parse JSON data
                JsonArray list = jsonObject.getAsJsonArray("list");

                // Store data of Each of 5 days in a 2D array
                int numDays = 5; // Number of days
                double[][] data = new double[numDays][5]; // 5 days and 5 data points (temp, temp_min, temp_max, pressure, humidity)
                String[] iconUrls = new String[numDays]; // Array to store icon URLs
                String[] weatherCondition = new String[numDays]; // Array to store weather condition
                int dayIndex = 0;

                for (int i = 0; i < list.size(); i++) {
                    JsonObject item = list.get(i).getAsJsonObject();
                    JsonObject main = item.getAsJsonObject("main");

                    data[dayIndex][0] = main.get("temp").getAsDouble();
                    data[dayIndex][1] = main.get("temp_min").getAsDouble();
                    data[dayIndex][2] = main.get("temp_max").getAsDouble();
                    data[dayIndex][3] = main.get("pressure").getAsDouble();
                    data[dayIndex][4] = main.get("humidity").getAsDouble();

                    JsonArray weatherArray = item.getAsJsonArray("weather");
                    JsonObject weather = weatherArray.get(0).getAsJsonObject();
                    String iconCode = weather.get("icon").getAsString(); // Icon of current weather
                    String weatherMain = weather.get("main").getAsString();

                    String baseIconUrl ="https://openweathermap.org/img/wn/"; // url for the icons
                    String iconUrl = baseIconUrl + iconCode + "@2x.png"; // final url for icon

                    // Storing icon URL in the 1D array
                    iconUrls[dayIndex] = iconUrl;
                    weatherCondition[dayIndex] = weatherMain;

                    if ((i + 1) % 8 == 0) {
                        dayIndex++;
                    }
                }

                // Call method to insert data into the database
                insertWeatherData(data, iconUrls, weatherCondition, latitude, longitude, city);

                // Fetch and display newly inserted data
                fetchWeatherData(latitude, longitude, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to delete old data from the table in the database based on insertion_time
    public static void deleteOldData() throws SQLException {
        try {
            connect(); // Call the connect() method to establish a database connection
            Instant sixHoursAgo = Instant.now().minus(Duration.ofHours(6));
            Timestamp sixHoursAgoTimestamp = Timestamp.from(sixHoursAgo);
            String deleteSql = "DELETE FROM weather_forecast WHERE insertion_time < ?";
            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setTimestamp(1, sixHoursAgoTimestamp);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(); // Call the close() method to close the database connection
        }
    }

    public void APIcall(double latitude, double longitude) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" +
                    latitude +
                    "&lon=" +
                    longitude +
                    "&appid=" +
                    APIkey +
                    "&units=" +
                    units;

            URL url = new URL(apiUrl);

            performAPICall(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void APIcall(String cityName) {
        try {
            URL apiUrl = new URL(
                    "https://api.openweathermap.org/data/2.5/forecast?q=" +
                            cityName +
                            "&appid=" +
                            APIkey);
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
        parseJSON(jsonObject, 0, 0, ""); // Assuming latitude, longitude, and city are not available in API response
        connection.disconnect();
    }

    public static void main(String[] args) {
        System.out.println("Program started...");
        DBweatherForecast weatherDataDAO = new DBweatherForecast();

        // Example API calls
        weatherDataDAO.APIcall(40.7128, -74.0060); // Example latitude and longitude
        weatherDataDAO.APIcall("New York"); // Example city name

        // Schedule data cleanup task every 6 hours
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                weatherDataDAO.deleteOldData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 6, TimeUnit.HOURS);

        System.out.println("Program finished...");
    }
}