package Src.OpenWeatherAPI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentWeatherAPI implements InterfaceAPI {
  @Override
  public void parseJSON(JsonObject jsonObject) {

    // This module Parses the JSON string returned by the API

    JsonObject coord = jsonObject.getAsJsonObject("coord");
    double lon = coord.get("lon").getAsDouble();
    double lat = coord.get("lat").getAsDouble();

    JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
    JsonObject weather = weatherArray.get(0).getAsJsonObject();
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

    JsonObject rain = jsonObject.getAsJsonObject("rain");

    JsonObject clouds = jsonObject.getAsJsonObject("clouds");
    int cloudsAll = clouds.get("all").getAsInt(); // Cloudiness

    int dt = jsonObject.get("dt").getAsInt(); // Time of data calculation

    JsonObject sys = jsonObject.getAsJsonObject("sys");
    String country = sys.get("country").getAsString(); // Country Codes (GB,JP etc)
    int sunrise = sys.get("sunrise").getAsInt(); // Sunrise Time
    int sunset = sys.get("sunset").getAsInt(); // Sunset Time

    int timezone = jsonObject.get("timezone").getAsInt(); // TimeZone

  }

  @Override
  public void APIcall(double latitude, double longitude) {

    try {
      // Create URL with latitude, longitude, and API key
      URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
          "&lon=" + longitude + "&appid=" + APIkey + "&units=" + units);

      // Open connection
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // Set request method
      connection.setRequestMethod("GET");

      // Get response code
      int responseCode = connection.getResponseCode();
      System.out.println("Response Code: " + responseCode);

      // Read response
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();

      // Parse JSON response
      Gson gson = new Gson();
      JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

      parseJSON(jsonObject);
      // Close connection
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /*Umair
   * I have not change anyhting just added function below
   */
  @Override
  public String getData(double latitude, double longitude) {
      StringBuilder data = new StringBuilder();
      try {
          // Create URL with latitude, longitude, and API key
          URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                  "&lon=" + longitude + "&appid=" + APIkey + "&units=" + units);
  
          // Open connection
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  
          // Set request method
          connection.setRequestMethod("GET");
  
          // Get response code
          int responseCode = connection.getResponseCode();
  
          // Read response
          if (responseCode == HttpURLConnection.HTTP_OK) { // Success
              BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
              String inputLine;
              StringBuilder response = new StringBuilder();
              while ((inputLine = in.readLine()) != null) {
                  response.append(inputLine);
              }
              in.close();
  
              // Parse JSON data
              Gson gson = new Gson();
              JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
  
              // Format data like SQL table
              data.append("LocationID, Timestamp, Temperature, FeelsLike, MinTemp, MaxTemp, Humidity, Pressure, WindSpeed, WindDeg, Sunrise, Sunset, WeatherDescription\n");
              data.append("1, "); // Assuming LocationID is 1
              data.append(System.currentTimeMillis() + ", "); // Timestamp
              data.append(jsonObject.getAsJsonObject("main").get("temp").getAsDouble() + ", "); // Temperature
              data.append(jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble() + ", "); // FeelsLike
              data.append(jsonObject.getAsJsonObject("main").get("temp_min").getAsDouble() + ", "); // MinTemp
              data.append(jsonObject.getAsJsonObject("main").get("temp_max").getAsDouble() + ", "); // MaxTemp
              data.append(jsonObject.getAsJsonObject("main").get("humidity").getAsInt() + ", "); // Humidity
              data.append(jsonObject.getAsJsonObject("main").get("pressure").getAsInt() + ", "); // Pressure
              data.append(jsonObject.getAsJsonObject("wind").get("speed").getAsDouble() + ", "); // WindSpeed
              data.append(jsonObject.getAsJsonObject("wind").get("deg").getAsInt() + ", "); // WindDeg
              data.append(jsonObject.getAsJsonObject("sys").get("sunrise").getAsInt() + ", "); // Sunrise
              data.append(jsonObject.getAsJsonObject("sys").get("sunset").getAsInt() + ", "); // Sunset
              data.append(jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString()); // WeatherDescription
  
          } else {
              data.append("GET request not worked");
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return data.toString();
  }
  

  public static void main(String[] args) {
    CurrentWeatherAPI test = new CurrentWeatherAPI();
    test.APIcall(23.56, 89.23);
  }
}