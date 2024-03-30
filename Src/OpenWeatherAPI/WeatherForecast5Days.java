package Src.OpenWeatherAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast5Days implements InterfaceAPI {

  @Override
  public void parseJSON(JsonObject jsonObject) {
    // Parsing values into variables
    JsonArray list = jsonObject.getAsJsonArray("list");

    // Store data of Each of 5 days in arrays
    int numDays = list.size();
    double[][] data = new double[numDays][5]; // 5 data points (temp, temp_min, temp_max, pressure, humidity)
    String[] iconUrls = new String[numDays]; // Array to store icon URLs
    String[] weatherMain = new String[numDays]; // Array to store weather main

    for (int i = 0; i < list.size(); i++) {
      JsonObject item = list.get(i).getAsJsonObject();
      JsonObject main = item.getAsJsonObject("main");

      data[i][0] = main.get("temp").getAsDouble();
      data[i][1] = main.get("temp_min").getAsDouble();
      data[i][2] = main.get("temp_max").getAsDouble();
      data[i][3] = main.get("pressure").getAsDouble();
      data[i][4] = main.get("humidity").getAsDouble();

      JsonArray weatherArray = item.getAsJsonArray("weather");
      JsonObject weather = weatherArray.get(0).getAsJsonObject();
      String iconCode = weather.get("icon").getAsString(); // Icon of current weather
      iconUrls[i] = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png"; // Store icon URL

      weatherMain[i] = weather.get("main").getAsString(); // Store weather main
    }

    // Printing the Data of Each of 5 Days
    for (int i = 0; i < numDays; i++) {
      System.out.println("DAY " + (i + 1));
      for (double value : data[i]) {
        System.out.print(value + " ");
      }
      System.out.println("Icon URL: " + iconUrls[i]);
      System.out.println("Weather Main: " + weatherMain[i]);
      System.out.println();
    }
  }

  @Override
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

      // Create URL object
      @SuppressWarnings("deprecation")
      URL url = new URL(apiUrl);

      // Create HttpURLConnection
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      // Get the response code
      int responseCode = conn.getResponseCode();
      performAPICall(url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void APIcall(String cityName) {
    try {
      @SuppressWarnings("deprecation")
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
    parseJSON(jsonObject);
    connection.disconnect();
  }

  public static void main(String[] args) {
    WeatherForecast5Days test = new WeatherForecast5Days();
    test.APIcall(44.34, 10.99);
  }
}
