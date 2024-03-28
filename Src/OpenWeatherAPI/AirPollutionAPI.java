package Src.OpenWeatherAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;

public class AirPollutionAPI implements InterfaceAPI, notificationInterface {

  @Override
  public void parseJSON(JsonObject jsonObject) {
    // Parse air pollution data
    JsonObject coordObject = jsonObject.getAsJsonObject("coord");
    double lat = coordObject.get("lat").getAsDouble();
    double lon = coordObject.get("lon").getAsDouble();
    System.out.println("coordinates: (" + lat + ", " + lon + ")");
    JsonArray listArray = jsonObject.getAsJsonArray("list");
    JsonObject firstItem = listArray.get(0).getAsJsonObject();
    long dt = firstItem.get("dt").getAsLong(); // date and time
    JsonObject main = firstItem.getAsJsonObject("main");
    int aqi = main.get("aqi").getAsInt(); // AQI
    JsonObject components = firstItem.getAsJsonObject("components");
    double co = components.get("co").getAsDouble();
    double no = components.get("no").getAsDouble();
    double no2 = components.get("no2").getAsDouble();
    double o3 = components.get("o3").getAsDouble();
    double so2 = components.get("so2").getAsDouble();
    double pm2_5 = components.get("pm2_5").getAsDouble();
    double pm10 = components.get("pm10").getAsDouble();
    double nh3 = components.get("nh3").getAsDouble();

    System.out.println("DT: " + dt);
    System.out.println("AQI: " + aqi);
    System.out.println("CO: " + co);
    System.out.println("NO: " + no);
    System.out.println("NO2: " + no2);
    System.out.println("O3: " + o3);
    System.out.println("SO2: " + so2);
    System.out.println("PM2.5: " + pm2_5);
    System.out.println("PM10: " + pm10);
    System.out.println("NH3: " + nh3);

    if (aqi > POOR_AIR_QUALITY_THRESHOLD) {
      generateNotification(aqi);
    }
  }

  @Override
  public void APIcall(double latitude, double longitude) {
    try {
      String apiUrl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" +
          latitude +
          "&lon=" +
          longitude +
          "&appid=" +
          APIkey +
          "&units=" +
          units;

      // Create URL object
      URL url = new URL(apiUrl);

      // Create HttpURLConnection
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      // Get the response code
      int responseCode = conn.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK) { // Success
        // Read response
        BufferedReader in = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();

        // Parse JSON using Gson
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser
            .parse(response.toString())
            .getAsJsonObject();
        parseJSON(jsonObject);
      } else {
        System.out.println("GET request not worked");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void APIcall(String city) {
    // to be decided
  }

  // notification interface function for poor weather quality
  public void generateNotification(int aqi) {
    JOptionPane.showMessageDialog(null, "The air quality for the region is not good with AQI = " + aqi);
  }

  public static void main(String[] args) {
    AirPollutionAPI AirPollution = new AirPollutionAPI();
    AirPollution.APIcall(34.56, 89.0);
  }
}
