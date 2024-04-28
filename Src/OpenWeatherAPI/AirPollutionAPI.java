package Src.OpenWeatherAPI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;

import Src.BusinessLogic.DisplayData;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.WeatherDataStorage.WeatherDataTxtStorage;

public class AirPollutionAPI implements InterfaceAPI, notificationInterface {

  public void parseJSON(JsonObject jsonObject, AirPollutionAPIData obj, String CityName) {

    // Parse air pollution data
    JsonObject coordObject = jsonObject.getAsJsonObject("coord");
    float lat = coordObject.get("lat").getAsFloat();
    float lon = coordObject.get("lon").getAsFloat();
    //System.out.println("coordinates: (" + lat + ", " + lon + ")");
    JsonArray listArray = jsonObject.getAsJsonArray("list");
    JsonObject firstItem = listArray.get(0).getAsJsonObject();
    int dt = firstItem.get("dt").getAsInt(); // date and time
    JsonObject main = firstItem.getAsJsonObject("main");
    int aqi = main.get("aqi").getAsInt(); // AQI
    JsonObject components = firstItem.getAsJsonObject("components");
    float co = components.get("co").getAsFloat();
    float no = components.get("no").getAsFloat();
    float no2 = components.get("no2").getAsFloat();
    float o3 = components.get("o3").getAsFloat();
    float so2 = components.get("so2").getAsFloat();
    float pm2_5 = components.get("pm2_5").getAsFloat();
    float pm10 = components.get("pm10").getAsFloat();
    float nh3 = components.get("nh3").getAsFloat();

    // System.out.println("dt: " + dt);
    // System.out.println("aqi: " + aqi);
    // System.out.println("co: " + co);
    // System.out.println("no: " + no);
    // System.out.println("no2: " + no2);
    // System.out.println("o3: " + o3);
    // System.out.println("so2: " + so2);
    // System.out.println("pm2.5: " + pm2_5);
    // System.out.println("pm10: " + pm10);
    // System.out.println("nh3: " + nh3);

    obj.setLocId(1);
    obj.setLatitude(lat);
    obj.setLongitude(lon);
    obj.setDt(dt);
    obj.setAqi(aqi);
    obj.setCo(co);
    obj.setNo(no);
    obj.setNo2(no2);
    obj.setO3(o3);
    obj.setSo2(so2);
    obj.setPm10(pm10);
    obj.setPm25(pm2_5);
    obj.setNh3(nh3);
    obj.setCityName(CityName);
    // if (aqi > POOR_AIR_QUALITY_THRESHOLD) {
    //   generateNotification(aqi);
    // }

    //System.out.println("lat: "+lat+" lon :"+lon);
    
  }

  // Old Method

  // @Override
  // public void APIcall(double latitude, double longitude) {
  // try {
  // String apiUrl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" +
  // latitude +
  // "&lon=" +
  // longitude +
  // "&appid=" +
  // APIkey +
  // "&units=" +
  // units;

  // // Create URL object
  // URL url = new URL(apiUrl);

  // // Create HttpURLConnection
  // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
  // conn.setRequestMethod("GET");

  // // Get the response code
  // int responseCode = conn.getResponseCode();

  // if (responseCode == HttpURLConnection.HTTP_OK) { // Success
  // // Read response
  // BufferedReader in = new BufferedReader(
  // new InputStreamReader(conn.getInputStream()));
  // String inputLine;
  // StringBuilder response = new StringBuilder();

  // while ((inputLine = in.readLine()) != null) {
  // response.append(inputLine);
  // }
  // in.close();

  // // Parse JSON using Gson
  // JsonParser parser = new JsonParser();
  // JsonObject jsonObject = parser
  // .parse(response.toString())
  // .getAsJsonObject();
  // parseJSON(jsonObject);
  // } else {
  // System.out.println("GET request not worked");
  // }
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }

  @Override
  public URL APIcall(double latitude, double longitude) {
    try {
      @SuppressWarnings("deprecation")
      // Create URL with latitude, longitude, and API key
      URL apiUrl = new URL(
          "http://api.openweathermap.org/data/2.5/air_pollution?lat=" +
              latitude +
              "&lon=" +
              longitude +
              "&appid=" +
              APIkey +
              "&units=" +
              units);
      return apiUrl;
    } catch (MalformedURLException e) {
      // Handle the MalformedURLException appropriately
      System.err.println("Malformed URL: " + e.getMessage());
      // Return null or throw an exception based on your application logic
      return null; // For example, returning null here
    } catch (Exception e) {
      // Handle other exceptions if necessary
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
      connection.disconnect();
      return jsonObject;
    } catch (IOException e) {
      // Handle the IOException appropriately
      e.printStackTrace();
      return null; // Return null or throw a more specific exception depending on your
                   // application's logic
    }
  }

  @Override
  public URL APIcall(String cityName) {
    try {
      @SuppressWarnings("deprecation")
      URL apiUrl = new URL(
          "https://api.openweathermap.org/data/2.5/weather?q=" +
              cityName +
              "&appid=" +
              APIkey);
      return apiUrl;
    } catch (MalformedURLException e) {
      // Handle the MalformedURLException appropriately
      System.err.println("Malformed URL: " + e.getMessage());
      // Return null or throw an exception based on your application logic
      return null; // For example, returning null here
    } catch (Exception e) {
      // Handle other exceptions if necessary
      e.printStackTrace();
      // Stop execution by throwing a RuntimeException
      throw new RuntimeException("Exception occurred while creating API URL");
    }
  }

  // notification interface function for poor weather quality
  public void generateNotification(int aqi) {
    JOptionPane.showMessageDialog(null, "The air quality for the region is not good with AQI = " + aqi);
  }

  public void searchAirPollution(double latitude, double longitude, String CityName, AirPollutionAPIData obj) {

    URL apiUrl = this.APIcall(latitude, longitude);
    JsonObject jsonObject = this.performAPICall(apiUrl);
    this.parseJSON(jsonObject, obj, CityName);
  }

  public static void main(String[] args) {

    AirPollutionAPI api = new AirPollutionAPI();
    URL apiUrl = api.APIcall(34.5, 74.3);
    JsonObject jsonObject = api.performAPICall(apiUrl);
    AirPollutionAPIData data = new AirPollutionAPIData();
    api.parseJSON(jsonObject, data, APIkey);

  }
}
