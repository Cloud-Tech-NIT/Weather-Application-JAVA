package Src.WeatherDataStorage;
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

public class DBCurrweatherData {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/weather_Cache";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "abc_123";
    private static final String API_KEY = "cc7d0c84d9aca07ad0bc1494b2af27a0";
    private static final String UNITS = "metric"; // or "imperial" depending on your preference

    private int locationIdCounter = 0;

    public void parseJSON(JsonObject jsonObject) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            JsonObject coord = jsonObject.getAsJsonObject("coord");
            double lat = coord.get("lat").getAsDouble();
            double lon = coord.get("lon").getAsDouble();
            String cityName = jsonObject.get("name").getAsString(); // Changed variable name to cityName
            // Check if data is already present in the database
            if (isDataPresent(connection, lat, lon, cityName)) {
                displayDataFromDatabase(connection, lat, lon, cityName); // Changed variable name to cityName
                return;
            }
            
            // Data parsed and get
            JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
            JsonObject weather = weatherArray.get(0).getAsJsonObject();
        
            int weatherID = weather.get("id").getAsInt(); // Changed variable name to weatherID
            String weatherMain = weather.get("main").getAsString(); // Rain, Snow etc
            String weatherDescription = weather.get("description").getAsString();
            String weatherIcon = weather.get("icon").getAsString(); // Icon of current weather
        
            JsonObject main = jsonObject.getAsJsonObject("main");
            double temp = main.get("temp").getAsDouble();
            double feelsLike = main.get("feels_like").getAsDouble();
            double tempMin = main.get("temp_min").getAsDouble();
            double tempMax = main.get("temp_max").getAsDouble();
            int pressure = main.get("pressure").getAsInt();
            int humidity = main.get("humidity").getAsInt();
            int visibility = jsonObject.get("visibility").getAsInt();
            JsonObject wind = jsonObject.getAsJsonObject("wind");
            double windSpeed = wind.get("speed").getAsDouble();
            int windDeg = wind.get("deg").getAsInt(); // Changed variable name to windDeg
            JsonObject rain = jsonObject.getAsJsonObject("rain");
            double rainVolume = rain != null ? rain.get("1h").getAsDouble() : 0; // Assume rain volume is in "1h" key
            JsonObject clouds = jsonObject.getAsJsonObject("clouds");
            int cloudsAll = clouds.get("all").getAsInt(); // Cloudiness
            int dt = jsonObject.get("dt").getAsInt(); // Time of data calculation
            JsonObject sys = jsonObject.getAsJsonObject("sys");
            String country = sys.get("country").getAsString(); // Country Codes (GB,JP etc)
            int sunrise = sys.get("sunrise").getAsInt(); // Sunrise Time
            int sunset = sys.get("sunset").getAsInt(); // Sunset Time
            int timezone = jsonObject.get("timezone").getAsInt(); // TimeZone
            

            // Increment and assign location ID
            locationIdCounter++;

            // Store data in the database
            String insertSql = "INSERT INTO Current_Weather_Data (loc_id, city_name, latitude, longitude, weather_main, weather_description, weather_icon, temperature, feels_like, temp_min, temp_max, pressure, humidity, visibility, wind_speed, rain, clouds_all, dt, country, sunrise, sunset, timezone) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                  
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) 
            {
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
    
    // Function to check if data already present in database
    private boolean isDataPresent(Connection connection, double latitude, double longitude, String cityName) {
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM Current_Weather_Data WHERE (latitude = ? AND longitude = ?) OR city_name = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setDouble(1, latitude);
            selectStatement.setDouble(2, longitude);
            selectStatement.setString(3, cityName);
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

    // Fetch data from database
    private void displayDataFromDatabase(Connection connection, double latitude, double longitude, String cityName)
     {

        try {
            String query = "SELECT * FROM Current_Weather_Data WHERE (latitude = ? AND longitude = ?) OR city_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            preparedStatement.setString(3, cityName);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Print the results
            while (resultSet.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    // Delete data in table after 6 hours
    public void deleteOldData()
     {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) 
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
        DBCurrweatherData fetcher = new DBCurrweatherData();

        // Schedule deletion of old data every 6 hours
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(fetcher::deleteOldData, 0, 6, TimeUnit.HOURS);

        // Example API call
        fetcher.APIcall(40.7128, -74.0060); // Example latitude and longitude
    }
}



