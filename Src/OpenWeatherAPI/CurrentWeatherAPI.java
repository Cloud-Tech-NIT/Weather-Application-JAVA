package Src.OpenWeatherAPI;

import Src.AppUI.Screen2Controller;
import Src.AppUI.mainscreenController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;

public class CurrentWeatherAPI implements InterfaceAPI, notificationInterface {

  private mainscreenController controller;

  // Constructor
  public CurrentWeatherAPI() {
    // No need to initialize controller here
  }

  // Method to set the mainscreenController reference
  public void setController(mainscreenController controller) {
    this.controller = controller;
  }
  // <changed> added print functionality//

  @Override
  public void parseJSON(JsonObject jsonObject) {
    // This module Parses the JSON string returned by the API

    JsonObject coord = jsonObject.getAsJsonObject("coord");
    double lon = coord.get("lon").getAsDouble();
    double lat = coord.get("lat").getAsDouble();

    JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
    JsonObject weather = weatherArray.get(0).getAsJsonObject();

    int WeatherID = weather.get("id").getAsInt();
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
    int WindDeg = wind.get("deg").getAsInt();
    JsonObject rain = jsonObject.getAsJsonObject("rain");

    JsonObject clouds = jsonObject.getAsJsonObject("clouds");
    int cloudsAll = clouds.get("all").getAsInt(); // Cloudiness

    int dt = jsonObject.get("dt").getAsInt(); // Time of data calculation

    JsonObject sys = jsonObject.getAsJsonObject("sys");
    String country = sys.get("country").getAsString(); // Country Codes (GB,JP etc)
    int sunrise = sys.get("sunrise").getAsInt(); // Sunrise Time
    int sunset = sys.get("sunset").getAsInt(); // Sunset Time
    int timezone = jsonObject.get("timezone").getAsInt(); // TimeZone
    String CityName = jsonObject.get("name").getAsString();

    if (visibility > POOR_WEATHER_THRESHOLD) {
      generateNotification(visibility);
    }
    if (controller != null) {
      controller.updateUI(controller, CityName, country, temp, weatherMain, weatherIcon, tempMin, tempMax, sunrise,
          sunset, pressure, humidity, windSpeed, lat, lon);
    }
  }

  @Override
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
              APIkey +
              "&units=" +
              units);
      performAPICall(url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void APIcall(String cityName) {
    try {
      @SuppressWarnings("deprecation")
      URL apiUrl = new URL(
          "https://api.openweathermap.org/data/2.5/weather?q=" +
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
    parseJSON(jsonObject);
    System.out.println(jsonObject);
    connection.disconnect();
  }

  // made notification interface function for poor weather quality
  @Override
  public void generateNotification(int visibility) {
    JOptionPane.showMessageDialog(
        null,
        "There is poor weather poor condition in " +
            " with visibility = " +
            visibility);
  }

  public static void main(String[] args) {
    CurrentWeatherAPI test = new CurrentWeatherAPI();
    test.APIcall("lahore");
  }
}
