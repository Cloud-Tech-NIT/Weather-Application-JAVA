package Src.OpenWeatherAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast5Days implements InterfaceAPI {

  @Override
  public void parseJSON(JsonObject jsonObject) {
    // Parsing values into variables
    int cnt = jsonObject.get("cnt").getAsInt(); // timestamps returned
    JsonArray list = jsonObject.getAsJsonArray("list");

    // Store data of Each of 5 days in a 2D array

    double[][] data = new double[5][5]; // 5 days and 5 data points (temp, temp_min, temp_max, pressure, humidity)

    int dayIndex = 0;
    for (int i = 0; i < list.size(); i++) {
      JsonObject item = list.get(i).getAsJsonObject();
      JsonObject main = item.getAsJsonObject("main");
      data[dayIndex][0] = main.get("temp").getAsDouble();
      data[dayIndex][1] = main.get("temp_min").getAsDouble();
      data[dayIndex][2] = main.get("temp_max").getAsDouble();
      data[dayIndex][3] = main.get("pressure").getAsDouble();
      data[dayIndex][4] = main.get("humidity").getAsDouble();
      if ((i + 1) % 8 == 0) {
        dayIndex++;
      }
    }

    // FOR Loop for Printing the Data of Each of 5 Days
    // int i = 1;
    // for (double[] dayData : data) {

    // System.out.println("DAY " + i);
    // for (double value : dayData) {
    // System.out.print(value + " ");
    // }
    // i = i + 1;
    // System.out.println();
    // }

  }

  @Override
  public void APIcall(double latitude, double longitude) {

    try {

      String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude
          + "&appid=" + APIkey + "&units=" + units;

      // Create URL object
      URL url = new URL(apiUrl);

      // Create HttpURLConnection
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      // Get the response code
      int responseCode = conn.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK) { // Success
        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();

        // Parse JSON response using Gson
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
        parseJSON(jsonObject);

      } else {
        System.out.println("GET request not worked");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // public static void main(String[] args) {
  // WeatherForecast5Days test = new WeatherForecast5Days();
  // test.APIcall(39.67, -78.98);
  // }
}
