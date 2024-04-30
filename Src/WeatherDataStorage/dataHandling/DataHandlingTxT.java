package Src.WeatherDataStorage.dataHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Src.WeatherDataStorage.CacheManager;

public class DataHandlingTxT implements CacheManager {
    Boolean found = false;

    @Override
    public Boolean checkData(String filename, double latitude, double longitude) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                double lat = 0.0, lon = 0.0;

                if (filename == "AirPollutionCo.txt")

                {
                    lat = Double.parseDouble(parts[0]);
                    lon = Double.parseDouble(parts[1]);
                    if (lat == latitude && lon == longitude) {
                        return true; // Data found
                    }
                  
                } else {
                    lat = Double.parseDouble(parts[1]);
                    lon = Double.parseDouble(parts[2]);
                    if (lat == latitude && lon == longitude) {
                        return true; // Data found
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Data not found
    }

    @Override
    public Boolean checkData(String filename, String cityName) {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                String city = parts[0];
                if (city.equalsIgnoreCase(cityName)) {
                    return true; // Data found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Data not found
    }

    @Override
    public String FetchData(String filename, double latitude, double longitude) {
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                double lat = 0.0, lon = 0.0;
                if (filename == "AirPollutionCo.txt")

                {
                    lat = Double.parseDouble(parts[0]);
                    lon = Double.parseDouble(parts[1]);
                    if (lat == latitude && lon == longitude) {
                        response.append(line).append("\n");
                    }
                } else {
                   

                    lat = Double.parseDouble(parts[1]);
                    lon = Double.parseDouble(parts[2]);
                    if (lat == latitude && lon == longitude) {
                        response.append(line).append("\n");
                    }
                }
               
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    @Override
    public String FetchData(String filename, String cityName) {
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                String city = parts[0];
                if (city.equalsIgnoreCase(cityName)) {
                    response.append(line).append("\n");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public static void main(String[] args) {
        DataHandlingTxT dataHandler = new DataHandlingTxT();
        // Test checkData 
        if (dataHandler.checkData("AirPollutionCo.txt", 31.52, 74.35)) {
            System.out.println("Data found in AirPollutionCo.txt ");
        } else {
            System.out.println("Data not found in AirPollutionCo.txt ");
        }

        // Test FetchData
        String airPollutionData = dataHandler.FetchData("AirPollutionCo.txt", 34.56, 89.0);
        System.out.println("Fetched Data from AirPollutionCo.txt:");
        System.out.println(airPollutionData);
    
      
    }
}
