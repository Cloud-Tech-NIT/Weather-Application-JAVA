package Src.OpenWeatherAPI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;


public class WeatherForecast5Days implements InterfaceAPI {

  public void parseJSON(JsonObject jsonObject, WeatherForecastAPIData obj, float latitude, float longitude) {

    JsonObject city = jsonObject.getAsJsonObject("city");
    float lat = latitude;
    float lon = longitude;
    String cityName = city.get("name").getAsString();

    obj.setLatitude(lat);
    obj.setLongitude(lon);
    obj.setCityName(cityName);

    // Parsing values into variables
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

      String baseIconUrl = "https://openweathermap.org/img/wn/"; // url for the icons
      String iconUrl = baseIconUrl + iconCode + "@2x.png"; // final url for icon

      // Storing icon URL in the 1D array
      iconUrls[dayIndex] = iconUrl;
      weatherCondition[dayIndex] = weatherMain;

      if ((i + 1) % 8 == 0) {
        dayIndex++;
      }
    }
    obj.setData(data);
    obj.setIconUrls(iconUrls);
    obj.setWeatherCondition(weatherCondition);
  }

  public void parseJSON(JsonObject jsonObject, WeatherForecastAPIData obj) {

    JsonObject city = jsonObject.getAsJsonObject("city");
    float lat = city.getAsJsonObject("coord").get("lat").getAsFloat();
    float lon = city.getAsJsonObject("coord").get("lon").getAsFloat();
    String cityName = city.get("name").getAsString();

    obj.setLatitude(lat);
    obj.setLongitude(lon);
    obj.setCityName(cityName);

    // Parsing values into variables
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

      String baseIconUrl = "https://openweathermap.org/img/wn/"; // url for the icons
      String iconUrl = baseIconUrl + iconCode + "@2x.png"; // final url for icon

      // Storing icon URL in the 1D array
      iconUrls[dayIndex] = iconUrl;
      weatherCondition[dayIndex] = weatherMain;

      if ((i + 1) % 8 == 0) {
        dayIndex++;
      }
    }
    obj.setData(data);
    obj.setIconUrls(iconUrls);
    obj.setWeatherCondition(weatherCondition);
  }

  @Override
  public URL APIcall(double latitude, double longitude) {
    try {
      @SuppressWarnings("deprecation")
      // Create URL with latitude, longitude, and API key
      URL apiUrl = new URL(
          "https://api.openweathermap.org/data/2.5/forecast?lat=" +
              latitude +
              "&lon=" +
              longitude +
              "&appid=" +
              APIkey +
              "&units=" +
              units);
      return apiUrl;
    } catch (MalformedURLException e) {
      System.err.println("Malformed URL: " + e.getMessage());
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      // Stop execution by throwing a RuntimeException
      throw new RuntimeException("Exception occurred while creating API URL");
    }
  }

  @Override
  public URL APIcall(String cityName) {
    try {
      @SuppressWarnings("deprecation")
      URL apiUrl = new URL(
          "https://api.openweathermap.org/data/2.5/forecast?q=" +
              cityName +
              "&appid=" +
              APIkey +
              "&units=" +
              units);
      return apiUrl;
    } catch (MalformedURLException e) {
      System.err.println("Malformed URL: " + e.getMessage());
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      // Stop execution by throwing a RuntimeException
      throw new RuntimeException("Exception occurred while creating API URL");
    }
  }

  public JsonObject performAPICall(URL apiUrl) {
    try {
      HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
      connection.setRequestMethod("GET");
      int responseCode = connection.getResponseCode();

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
      connection.disconnect();
      return jsonObject;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void SearchByCity(String CityName, WeatherForecastAPIData obj) {
    URL apiUrl = this.APIcall(CityName);
    JsonObject jsonObject = this.performAPICall(apiUrl);
    this.parseJSON(jsonObject, obj);
  }

  public void SearchByCoord(double latitude, double longitude, WeatherForecastAPIData obj) {
    URL apiUrl = this.APIcall(latitude, longitude);
    JsonObject jsonObject = this.performAPICall(apiUrl);
    float lat = (float) latitude;
    float lon = (float) longitude;
    this.parseJSON(jsonObject, obj, lat, lon);
  }

  public static void main(String[] args) {
    WeatherForecast5Days test = new WeatherForecast5Days();
    URL url = test.APIcall(44.34, 10.99);
    JsonObject jsonObject = test.performAPICall(url);
    WeatherForecastAPIData temp = new WeatherForecastAPIData();
    test.parseJSON(jsonObject, temp);
  }
}