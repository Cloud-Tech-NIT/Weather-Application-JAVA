package Src.WeatherDataStorage.dataHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.WeatherDataStorage.StoreTxt;

public class WeatherDataTxtStorage implements StoreTxt {

    public static void main(String[] args) {
        // Call the deleteOldData function
        // deleteOldData();
    }

    @Override
    public void storeAirPollutionData(AirPollutionAPIData airPollutionData) {
        // Extract data from the AirPollutionAPIData object
        float lat = airPollutionData.getLatitude();
        float lon = airPollutionData.getLongitude();
        int dt = airPollutionData.getDt();
        int aqi = airPollutionData.getAqi();
        float co = airPollutionData.getCo();
        float no = airPollutionData.getNo();
        float no2 = airPollutionData.getNo2();
        float o3 = airPollutionData.getO3();
        float so2 = airPollutionData.getSo2();
        float pm2_5 = airPollutionData.getPm25();
        float pm10 = airPollutionData.getPm10();
        float nh3 = airPollutionData.getNh3();
        String city = airPollutionData.getCityName();
        DataHandlingTxT cache = new DataHandlingTxT();
        boolean dataExists = cache.checkData("AirPollutionCo.txt", lat, lon);

        if (!dataExists) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());

            // Format the data string
            String data = city + "_" + lat + "_" + lon + "_" + timestamp + "_" + dt + "_" + aqi + "_" + co + "_" + no
                    + "_" + no2
                    + "_" + o3 + "_" + so2 + "_" + pm2_5 + "_" + pm10 + "_" + nh3;

            // Write data to the file
            try (FileWriter writer = new FileWriter("AirPollutionCo.txt", true)) {
                // Append data to the file
                writer.write(data + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void storeCurrentWeatherData(CurrentWeatherAPIData currentWeatherData) {
        // Extract data from the CurrentWeatherAPIData object
        double lat = currentWeatherData.getLatitude();
        double lon = currentWeatherData.getLongitude();
        int weatherID = currentWeatherData.getWeatherID();
        String weatherMain = currentWeatherData.getWeatherMain();
        String weatherDescription = currentWeatherData.getWeatherDescription();
        double temp = currentWeatherData.getTemperature();
        double feelsLike = currentWeatherData.getFeelsLike();
        double tempMin = currentWeatherData.getTempMin();
        double tempMax = currentWeatherData.getTempMax();
        int pressure = currentWeatherData.getPressure();
        int humidity = currentWeatherData.getHumidity();
        int visibility = currentWeatherData.getVisibility();
        double windSpeed = currentWeatherData.getWindSpeed();
        int windDeg = currentWeatherData.getWindDeg();
        int cloudsAll = currentWeatherData.getCloudsAll();
        int dt = currentWeatherData.getDt();
        String country = currentWeatherData.getCountry();
        int sunrise = currentWeatherData.getSunrise();
        int sunset = currentWeatherData.getSunset();
        int timezone = currentWeatherData.getTimezone();
        String cityName = currentWeatherData.getCityName();
        String icon = currentWeatherData.getWeatherIcon();

        DataHandlingTxT cache = new DataHandlingTxT();
        boolean dataExists = cache.checkData("CurrentWeatherData.txt", lat, lon);

        if (!dataExists) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());

            // Format the data string
            String data = cityName + "_" + lat + "_" + lon + "_" + timestamp + "_" +
                    weatherID + "_" + weatherMain + "_" + weatherDescription + "_" +
                    temp + "_" + feelsLike + "_" + tempMin + "_" + tempMax + "_" +
                    pressure + "_" + humidity + "_" + visibility + "_" + windSpeed + "_" +
                    windDeg + "_" + cloudsAll + "_" + dt + "_" + country + "_" +
                    sunrise + "_" + sunset + "_" + timezone + "_" + icon;

            System.out.println("inserting data in txt file");
            // Write data to the file
            try (FileWriter writer = new FileWriter("CurrentWeatherData.txt", true)) {
                // Append data to the file
                writer.write(data + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Data already exists for the provided coordinates.");
        }
    }

    @Override
    public void storeWeatherForecastData(WeatherForecastAPIData data) {

        double[][] forecastData = data.getData();
        String[] iconUrls = data.getIconUrls();
        String[] weatherConditions = data.getWeatherCondition();
        double lat = data.getLatitude();
        double lon = data.getLongitude();
        String cityName = data.getCityName();

        DataHandlingTxT cache = new DataHandlingTxT();
        boolean dataExists = cache.checkData("WeatherForecastData.txt", lat, lon);

        if (!dataExists) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());

            StringBuilder dataLine = new StringBuilder();

            // Append city, latitude, longitude, and timestamp
            dataLine.append(cityName).append("_").append(lat).append("_").append(lon).append("_").append(timestamp)
                    .append("_");

            // Append forecast data for each day
            for (int i = 0; i < forecastData.length; i++) {
                dataLine.append("#_");
                for (int j = 0; j < forecastData[i].length; j++) {
                    dataLine.append(forecastData[i][j]).append("_");
                }
                dataLine.append(iconUrls[i]).append("_").append(weatherConditions[i]).append("_");
            }

            // Write data to the file
            try (FileWriter writer = new FileWriter("WeatherForecastData.txt", true)) {
                writer.write(dataLine.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteOldData() {
        String[] filesToDeleteFrom = { "AirPollutionCo.txt", "CurrentWeatherData.txt", "WeatherForecastData.txt" };
        long fiveHoursInMillis = 5 * 60 * 60 * 1000;

        for (String filePath : filesToDeleteFrom) {
            File file = new File(filePath);
            StringBuilder updatedContent = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("_");
                    if (parts.length >= 4) {
                        String timestampString = "";
                        timestampString = parts[3] + "_" + parts[4];
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        Date timestamp = format.parse(timestampString);
                        long difference = System.currentTimeMillis() - timestamp.getTime();
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
}
