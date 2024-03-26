package Src.OpenWeatherAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AirPollutionAPI implements InterfaceAPI {
  @Override
  public void parseJSON(JsonObject jsonObject) {
    // Parse air pollution data
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
  }

  @Override
  public void APIcall(double latitude, double longitude) {
    try {

      String apiUrl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + latitude + "&lon=" + longitude
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
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();

        // Parse JSON using Gson
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(response.toString()).getAsJsonObject();
        parseJSON(jsonObject);

      } else {
        System.out.println("GET request not worked");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    AirPollutionAPI AirPollution = new AirPollutionAPI();
    AirPollution.APIcall(34.56, 89.0);
  }
    
  /*Umair
   * I have not changed anything just added function below
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

            // Append JSON data to StringBuilder
            data.append(response.toString());

        } else {
            data.append("GET request not worked");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return data.toString();
}

}