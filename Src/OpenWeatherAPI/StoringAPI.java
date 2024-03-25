package Src.OpenWeatherAPI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* 
 * Storing API
 * class to implement getData() and store in text files
 */
public class StoringAPI {
    public static void main(String[] args) {
        AirPollutionAPI airPollutionAPI = new AirPollutionAPI();
        CurrentWeatherAPI currentWeatherAPI = new CurrentWeatherAPI();
        WeatherForecast5Days weatherForecast5Days = new WeatherForecast5Days();

        double latitude = 34.56;
        double longitude = 89.0;

        String airPollutionData = airPollutionAPI.getData(latitude, longitude);
        String currentWeatherData = currentWeatherAPI.getData(latitude, longitude);
        String weatherForecastData = weatherForecast5Days.getData(latitude, longitude);

        // Store data in a text file
        storeDataToFile("WeatherDataStorage/air_pollution_data.txt", "Air Pollution Data", airPollutionData);
        storeDataToFile("WeatherDataStorage/current_weather_data.txt", "Current Weather Data", currentWeatherData);
        storeDataToFile("WeatherDataStorage/weather_forecast_data.txt", "Weather Forecast Data", weatherForecastData);
    }

    private static void storeDataToFile(String fileName, String heading, String data) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write heading
            bufferedWriter.write(heading + ":\n");

            // Write data
            bufferedWriter.write(data);

            bufferedWriter.close();
            System.out.println("Data stored successfully in " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

