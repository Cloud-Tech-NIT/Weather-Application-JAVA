package Src.WeatherDataStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDataTxtStorage {

    public static void main(String[] args) {
        // Call the deleteOldData function
        deleteOldData();
    }

    public static void storeAirPollutionData(
            double lat, double lon, long dt, int aqi, double co, double no, double no2,
            double o3, double so2, double pm2_5, double pm10, double nh3) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());

        // Format the data string
        String data = lat + "_" + lon + "_" + timestamp + "_" + dt + "_" + aqi + "_" +
                co + "_" + no + "_" + no2 + "_" + o3 + "_" + so2 + "_" +
                pm2_5 + "_" + pm10 + "_" + nh3;

        // Write data to the file
        try (FileWriter writer = new FileWriter("AirPollutionCo.txt", true)) {
            // Append data to the file
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void storeCurrentWeatherData(
            double lat, double lon, int WeatherID, String weatherMain, String weatherDescription,
            double temp, double feelsLike, double tempMin, double tempMax, int pressure, int humidity,
            int visibility, double windSpeed, int WindDeg, int cloudsAll, int dt, String country,
            int sunrise, int sunset, int timezone, String CityName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());

        // Format the data string
        String data = CityName + "_" + lat + "_" + lon + "_" + timestamp + "_" +
                WeatherID + "_" + weatherMain + "_" + weatherDescription + "_" +
                temp + "_" + feelsLike + "_" + tempMin + "_" + tempMax + "_" +
                pressure + "_" + humidity + "_" + visibility + "_" + windSpeed + "_" +
                WindDeg + "_" + cloudsAll + "_" + dt + "_" + country + "_" +
                sunrise + "_" + sunset + "_" + timezone;

        // Write data to the file
        try (FileWriter writer = new FileWriter("CurrentWeatherData.txt", true)) {
            // Append data to the file
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOldData() {
        String filePath = "AirPollutionCo.txt";
        File file = new File(filePath);

        // Get current time
        long currentTime = System.currentTimeMillis();

        // Get 5 hours in milliseconds
        long fiveHoursInMillis = 5 * 60 * 60 * 1000;

        // Create a StringBuilder to store the updated content
        StringBuilder updatedContent = new StringBuilder();

        // Read file and delete old entries
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by _
                String[] parts = line.split("_");
                if (parts.length >= 4) {
                    // Extract timestamp from the line
                    String timestampString = parts[2] + "_" + parts[3]; // Assuming timestamp is at index 2 and 3
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    Date timestamp = format.parse(timestampString);

                    // Calculate difference between current time and timestamp
                    long difference = currentTime - timestamp.getTime();

                    // If the difference is less than 5 hours, keep the entry
                    if (difference < fiveHoursInMillis) {
                        updatedContent.append(line).append("\n");
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Write the updated content back to the original file
        try (FileWriter writer = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
