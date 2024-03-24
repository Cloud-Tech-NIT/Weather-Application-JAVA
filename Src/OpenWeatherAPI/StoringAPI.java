package Src.OpenWeatherAPI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/* 
 * storing api
 * class to implement getdata() and store in text file
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
        storeDataToFile(airPollutionData + currentWeatherData + weatherForecastData);
    }

    private static void storeDataToFile(String data) {
        try {
            FileWriter fileWriter = new FileWriter("api_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            System.out.println("Data stored successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
