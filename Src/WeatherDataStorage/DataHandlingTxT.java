package Src.WeatherDataStorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataHandlingTxT implements CacheManager {
  Boolean found = false;

    @Override
    public Boolean checkData(double latitude, double longitude) {
      try (BufferedReader reader = new BufferedReader(new FileReader("AirPollutionCo.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("_");
            double lat = Double.parseDouble(parts[0]);
            double lon = Double.parseDouble(parts[1]);
            if (lat == latitude && lon == longitude) {
                return true; // Data found
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false; // Data not found
    }

    @Override
    public Boolean checkData(String cityName) {
        // This method can be implemented similarly to check for data based on city name
        return false;
    }

    @Override
    public String FetchData(double latitude, double longitude) {
      StringBuilder response = new StringBuilder();
      try {
          BufferedReader reader = new BufferedReader(new FileReader("AirPollutionCo.txt"));
          String line;
          while ((line = reader.readLine()) != null) {
              String[] parts = line.split("_");
              double lat = Double.parseDouble(parts[0]);
              double lon = Double.parseDouble(parts[1]);
              if (lat == latitude && lon == longitude) {
                  response.append(line).append("\n");
              }
          }
          reader.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return response.toString();
  }

  @Override
  public String FetchData(String cityName)
  {
    StringBuilder response = new StringBuilder();
    return response.toString();
  }
   
    public static void main(String[] args) {
      DataHandlingTxT dataHandler = new DataHandlingTxT();
      boolean dataExists = dataHandler.checkData(31.52, 74.35);
      if (dataExists) {
          System.out.println("Data found");
      } else {
          System.out.println("Data not found");
      }
      // Test fetchData method
      String fetchedData = dataHandler.FetchData(31.52,74.35);
      System.out.println("Fetched Data:");
      System.out.println(fetchedData);
  }
}
