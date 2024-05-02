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

import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;

public class CurrentWeatherAPI implements InterfaceAPI {
  public void parseJSON(JsonObject jsonObject, CurrentWeatherAPIData obj) {
    // This module Parses the JSON string returned by the API

    String country = "US"; // Default country name
    String cityName = "New York"; // Default city name

    // Coordinates Section
    JsonObject coord = jsonObject.getAsJsonObject("coord");
    float lon = coord.get("lon").getAsFloat();
    float lat = coord.get("lat").getAsFloat();

    // Weather Section
    JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
    JsonObject weather = weatherArray.get(0).getAsJsonObject();
    int weatherId = weather.get("id").getAsInt();
    String weatherMain = weather.get("main").getAsString(); // Rain, Snow, etc.
    String weatherDescription = weather.get("description").getAsString();

    // Icon Section
    String iconCode = weather.get("icon").getAsString(); // Icon of current weather
    String baseIconUrl = "https://openweathermap.org/img/wn/"; // URL for the icons
    String iconUrl = baseIconUrl + iconCode + "@2x.png"; // Final URL for icon

    // Main section
    JsonObject main = jsonObject.getAsJsonObject("main");
    float temp = main.get("temp").getAsFloat();
    float feelsLike = main.get("feels_like").getAsFloat();
    float tempMin = main.get("temp_min").getAsFloat();
    float tempMax = main.get("temp_max").getAsFloat();
    int pressure = main.get("pressure").getAsInt();
    int humidity = main.get("humidity").getAsInt();
    int visibility = jsonObject.get("visibility").getAsInt();

    JsonObject wind = jsonObject.getAsJsonObject("wind");
    float windSpeed = wind.get("speed").getAsFloat();
    int windDeg = wind.get("deg").getAsInt();
    JsonObject rainP = jsonObject.getAsJsonObject("rain");
    float rain1h = 0.0f; // Default value for rain1h
    if (rainP != null && rainP.has("1h")) {
      rain1h = rainP.get("1h").getAsFloat();
    }

    JsonObject clouds = jsonObject.getAsJsonObject("clouds");
    int cloudsAll = clouds.get("all").getAsInt(); // Cloudiness
    int dt = jsonObject.get("dt").getAsInt(); // Time of data calculation
    JsonObject sys = jsonObject.getAsJsonObject("sys");

    // Check if "country" field exists and is not empty
    if (sys.has("country") && !sys.get("country").getAsString().isEmpty()) {
      country = sys.get("country").getAsString(); // Update country if valid
    }

    // Check if "name" field exists and is not empty
    if (jsonObject.has("name") && !jsonObject.get("name").getAsString().isEmpty()) {
      cityName = jsonObject.get("name").getAsString(); // Update city name if valid
    }
    int sunrise = sys.get("sunrise").getAsInt(); // Sunrise Time
    int sunset = sys.get("sunset").getAsInt(); // Sunset Time
    int timezone = jsonObject.get("timezone").getAsInt(); // TimeZone

    // Setting values in the object
    obj.setLocId(1);
    obj.setCityName(cityName);
    obj.setLatitude(lat);
    obj.setLongitude(lon);
    obj.setWeatherID(weatherId);
    obj.setWeatherDescription(weatherDescription);
    obj.setWeatherIcon(iconUrl);
    obj.setWeatherMain(weatherMain);
    obj.setTemperature(temp);
    obj.setFeelsLike(feelsLike);
    obj.setTempMax(tempMax);
    obj.setTempMin(tempMin);
    obj.setPressure(pressure);
    obj.setHumidity(humidity);
    obj.setVisibility(visibility);
    obj.setWindSpeed(windSpeed);
    obj.setWindDeg(windDeg);
    obj.setRain(rain1h);
    obj.setCloudsAll(cloudsAll);
    obj.setDt(dt);
    obj.setCountry(country); // Set the country name
    obj.setSunrise(sunrise);
    obj.setSunset(sunset);
    obj.setTimezone(timezone);
  }

  @Override
  public URL APIcall(double latitude, double longitude) {
    try {
      // Create URL with latitude, longitude, and API key
      URL apiUrl = new URL(
          "https://api.openweathermap.org/data/2.5/weather?lat=" +
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
      URL apiUrl = new URL(
          "https://api.openweathermap.org/data/2.5/weather?q=" +
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

  public void SearchByCity(String CityName, CurrentWeatherAPIData obj) {
    URL apiUrl = this.APIcall(CityName);
    JsonObject jsonObject = this.performAPICall(apiUrl);
    this.parseJSON(jsonObject, obj);
  }

  public void SearchByCoord(double latitude, double longitude, CurrentWeatherAPIData obj) {
    URL apiUrl = this.APIcall(latitude, longitude);
    JsonObject jsonObject = this.performAPICall(apiUrl);
    this.parseJSON(jsonObject, obj);
  }

  public static void main(String[] args) {
    CurrentWeatherAPI test = new CurrentWeatherAPI();
    URL apiUrl = test.APIcall(33.2, 19.34);
    JsonObject jsonObject = test.performAPICall(apiUrl);
    CurrentWeatherAPIData obj = new CurrentWeatherAPIData();
    test.parseJSON(jsonObject, obj);
  }
}
