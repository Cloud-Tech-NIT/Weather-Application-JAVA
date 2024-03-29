package Src.WeatherDataStorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataHandlingTxT implements CacheManager {
    Boolean found = false;

    @Override
    public Boolean checkData(String filename, double latitude, double longitude) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                double lat = 0.0, lon = 0.0;

                if (filename == "CurrentWeatherData.txt")

                {
                    lat = Double.parseDouble(parts[1]);
                    lon = Double.parseDouble(parts[2]);
                } else {
                    lat = Double.parseDouble(parts[0]);
                    lon = Double.parseDouble(parts[1]);
                }
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
                if (filename == "CurrentWeatherData.txt")

                {
                    lat = Double.parseDouble(parts[1]);
                    lon = Double.parseDouble(parts[2]);
                    if (lat == latitude && lon == longitude) {
                        response.append(line).append("\n");
                    }
                } else {
                    lat = Double.parseDouble(parts[0]);
                    lon = Double.parseDouble(parts[1]);
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
        // Test checkData method for AirPollutionCo.txt
        if (dataHandler.checkData("AirPollutionCo.txt", 31.52, 74.35)) {
            System.out.println("Data found in AirPollutionCo.txt ");
        } else {
            System.out.println("Data not found in AirPollutionCo.txt ");
        }
    
        // Test FetchData method for AirPollutionCo.txt
        String airPollutionData = dataHandler.FetchData("AirPollutionCo.txt", 34.56, 89.0);
        System.out.println("Fetched Data from AirPollutionCo.txt:");
        System.out.println(airPollutionData);
    
        // Test checkData method for CurrentWeatherData.txt
        if (dataHandler.checkData("CurrentWeatherData.txt", 40.71, -74.01)) {
            System.out.println("Data found in CurrentWeatherData.txt");
        } else {
            System.out.println("Data not found in CurrentWeatherData.txt");
        }
    
        // Test FetchData method for CurrentWeatherData.txt by latitude and longitude
        String currentWeatherData1 = dataHandler.FetchData("CurrentWeatherData.txt", 31.54, 74.34);
        System.out.println("Fetched Data from CurrentWeatherData.txt:");
        System.out.println(currentWeatherData1);
    
        // Test FetchData method for CurrentWeatherData.txt by city name
        String currentWeatherData2 = dataHandler.FetchData("CurrentWeatherData.txt", "Lahore");
        System.out.println("Fetched Data from CurrentWeatherData.txt for city Lahore:");
        System.out.println(currentWeatherData2);
    }
}
