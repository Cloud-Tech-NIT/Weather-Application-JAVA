package Src.WeatherDataStorage.dataHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;

import Src.WeatherDataStorage.CacheManager;

public class DataHandlingTxT implements CacheManager {
    Boolean found = false;
    private static final String AIR_POLLUTION_FILE = "AirPollutionCo.txt";
    private static final String CURRENT_WEATHER_FILE = "CurrentWeatherData.txt";
    private static final String WEATHER_FORECAST_FILE = "WeatherForecastData.txt";

    @Override
    public Boolean checkAirPollutionData(double latitude, double longitude) {
        return checkData(AIR_POLLUTION_FILE, latitude, longitude);
    }

    @Override
    public Boolean checkCurrentWeatherData(double latitude, double longitude) {
        return checkData(CURRENT_WEATHER_FILE, latitude, longitude);
    }

    @Override
    public Boolean checkWeatherForecastData(double latitude, double longitude) {
        return checkData(WEATHER_FORECAST_FILE, latitude, longitude);
    }

    @Override
    public Boolean checkCurrentWeatherData(String cityName) {
        return checkData(CURRENT_WEATHER_FILE, cityName);
    }

    @Override
    public Boolean checkWeatherForecastData(String cityName) {
        return checkData(WEATHER_FORECAST_FILE, cityName);
    }

    @Override
    public Boolean checkAirPollutionData(String cityName) {
        return checkData(WEATHER_FORECAST_FILE, cityName);
    }

    @Override
    public void fetchAirPollutionData(AirPollutionAPIData AirPoll, double latitude, double longitude) {
        String data = FetchData(AIR_POLLUTION_FILE, latitude, longitude);
        if (data != null && !data.isEmpty()) {
            populateAirPollutionData(AirPoll, data);
        }
    }

    @Override
    public void fetchAirPollutionData(AirPollutionAPIData AirPoll, String cityName) {
        String data = FetchData(AIR_POLLUTION_FILE, cityName);
        if (data != null && !data.isEmpty()) {
            populateAirPollutionData(AirPoll, data);

        }

    }

    private void populateAirPollutionData(AirPollutionAPIData airPollutionData, String data) {
        String[] parts = data.split("_");
        if (parts.length >= 15) {
            airPollutionData.setLatitude(Float.parseFloat(parts[0]));
            airPollutionData.setLongitude(Float.parseFloat(parts[1]));
            airPollutionData.setDt(Integer.parseInt(parts[4]));
            airPollutionData.setAqi(Integer.parseInt(parts[5]));
            airPollutionData.setCo(Float.parseFloat(parts[6]));
            airPollutionData.setNo(Float.parseFloat(parts[7]));
            airPollutionData.setNo2(Float.parseFloat(parts[8]));
            airPollutionData.setO3(Float.parseFloat(parts[9]));
            airPollutionData.setSo2(Float.parseFloat(parts[10]));
            airPollutionData.setPm25(Float.parseFloat(parts[11]));
            airPollutionData.setPm10(Float.parseFloat(parts[12]));
            airPollutionData.setNh3(Float.parseFloat(parts[13]));
            airPollutionData.setCityName(parts[14]);

        }
    }

    @Override
    public void fetchCurrentWeatherData(CurrentWeatherAPIData CurrentWeather, double latitude, double longitude) {
        String data = FetchData(CURRENT_WEATHER_FILE, latitude, longitude);
        if (data != null && !data.isEmpty()) {
            populateCurrentWeatherData(CurrentWeather, data);
        }
        System.out.println("city I am here");

    }

    @Override
    public void fetchCurrentWeatherData(CurrentWeatherAPIData CurrentWeather, String cityName) {
        String data = FetchData(CURRENT_WEATHER_FILE, cityName);
        if (data != null && !data.isEmpty()) {
            populateCurrentWeatherData(CurrentWeather, data);

        }

    }

    private void populateCurrentWeatherData(CurrentWeatherAPIData currentWeather, String data) {
        String[] parts = data.split("_");
        if (parts.length >= 23) { // Ensure all necessary parts are present
            currentWeather.setCityName(parts[0]);
            currentWeather.setLatitude(Float.parseFloat(parts[1]));
            currentWeather.setLongitude(Float.parseFloat(parts[2]));
            currentWeather.setWeatherID(Integer.parseInt(parts[5]));
            currentWeather.setWeatherMain(parts[6]);
            currentWeather.setWeatherDescription(parts[7]);
            currentWeather.setTemperature(Float.parseFloat(parts[8]));
            currentWeather.setFeelsLike(Float.parseFloat(parts[9]));
            currentWeather.setTempMin(Float.parseFloat(parts[10]));
            currentWeather.setTempMax(Float.parseFloat(parts[11]));
            currentWeather.setPressure(Integer.parseInt(parts[12]));
            currentWeather.setHumidity(Integer.parseInt(parts[13]));
            currentWeather.setVisibility(Integer.parseInt(parts[14]));
            currentWeather.setWindSpeed(Float.parseFloat(parts[15]));
            currentWeather.setWindDeg(Integer.parseInt(parts[16]));
            currentWeather.setCloudsAll(Integer.parseInt(parts[17]));
            currentWeather.setDt(Integer.parseInt(parts[18]));
            currentWeather.setCountry(parts[19]);
            currentWeather.setSunrise(Integer.parseInt(parts[20]));
            currentWeather.setSunset(Integer.parseInt(parts[21]));
            // currentWeather.setTimezone(Integer.parseInt(parts[22]));
            currentWeather.setTimezone(Integer.parseInt(parts[22].trim()));
        }
    }

    @Override
    public void fetchWeatherForecastData(WeatherForecastAPIData Forecast, double latitude, double longitude) {
        String data = FetchData(WEATHER_FORECAST_FILE, latitude, longitude);
        if (data != null && !data.isEmpty()) {
            populateWeatherForecastData(Forecast, data);
        }
        System.out.println("city I am here");

    }

    @Override
    public void fetchWeatherForecastData(WeatherForecastAPIData Forecast, String cityName) {
        String data = FetchData(WEATHER_FORECAST_FILE, cityName);
        if (data != null && !data.isEmpty()) {
            populateWeatherForecastData(Forecast, data);

        }
        System.out.println("I am here");

    }

    public void populateWeatherForecastData(WeatherForecastAPIData forecast, String data) {
        String[] parts = data.split("_");
        int index = 0;
        String cityName = parts[index++];
        float lat = Float.parseFloat(parts[index++]);
        float lon = Float.parseFloat(parts[index++]);
        int timestamp = Integer.parseInt(parts[index++]);
        index++;
        forecast.setCityName(cityName);
        forecast.setLatitude(lat);
        forecast.setLongitude(lon);
        forecast.setDt(timestamp);

        List<double[]> forecastDataList = new ArrayList<>();
        List<String> iconUrlsList = new ArrayList<>();
        List<String> weatherConditionsList = new ArrayList<>();

        while (index < parts.length) {
            if (parts[index].equals("#")) {
                index++; // Skip the '#' separator
                double[] dayForecastData = new double[5];
                int j = 0;
                while (j < 5 && !parts[index].isEmpty()) {
                    dayForecastData[j++] = Double.parseDouble(parts[index++]);
                }
                forecastDataList.add(dayForecastData);
                String iconUrl = parts[index++];
                iconUrlsList.add(iconUrl);
                String weatherCondition = parts[index++];
                weatherConditionsList.add(weatherCondition);
            } else {
                index++;
            }
        }

        double[][] forecastData = forecastDataList.toArray(new double[forecastDataList.size()][]);
        String[] iconUrls = iconUrlsList.toArray(new String[iconUrlsList.size()]);
        String[] weatherConditions = weatherConditionsList.toArray(new String[weatherConditionsList.size()]);

        forecast.setData(forecastData);
        forecast.setIconUrls(iconUrls);
        forecast.setWeatherCondition(weatherConditions);
    }

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

                } else {
                    lat = Double.parseDouble(parts[1]);
                    lon = Double.parseDouble(parts[2]);

                }

                lat = Math.round(lat * 100.0) / 100.0;
                lon = Math.round(lon * 100.0) / 100.0;

                // Check if rounded latitude and longitude match the provided latitude and
                // longitude
                if (Math.abs(lat - latitude) < 0.01 && Math.abs(lon - longitude) < 0.01) {
                    return true; // Data found
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Data not found
    }

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

                } else {

                    lat = Double.parseDouble(parts[1]);
                    lon = Double.parseDouble(parts[2]);

                }
                lat = Math.round(lat * 100.0) / 100.0;
                lon = Math.round(lon * 100.0) / 100.0;

                // Check if rounded latitude and longitude match the provided latitude and
                // longitude
                if (Math.abs(lat - latitude) < 0.01 && Math.abs(lon - longitude) < 0.01) {
                    response.append(line).append("\n");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public String FetchData(String filename, String cityName) {
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                String city = "";
                if (filename == "AirPollutionCo.txt") {
                    city = parts[14];
                } else {
                    city = parts[0];
                }

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
        // DataHandlingTxT dataHandler = new DataHandlingTxT();
        // // Test checkData
        // if (dataHandler.checkAirPollutionData(34, 75)) {
        // System.out.println("Data found in AirPollutionCo.txt ");
        // } else {
        // System.out.println("Data not found in AirPollutionCo.txt ");
        // }

        DataHandlingTxT dataHandler = new DataHandlingTxT();
        AirPollutionAPIData airPollutionData = new AirPollutionAPIData();

        // Test fetchAirPollutionData method
        double latitude = 31.5;
        double longitude = 74.35;
        dataHandler.fetchAirPollutionData(airPollutionData,"Model Town");

        // Print the fetched air pollution data
        System.out.println("Air Pollution Data:");
        System.out.println("AQI: " + airPollutionData.getAqi());
        System.out.println("CO: " + airPollutionData.getCo());
        System.out.println("NO: " + airPollutionData.getNo());
        System.out.println("NO2: " + airPollutionData.getNo2());
        System.out.println("O3: " + airPollutionData.getO3());
        System.out.println("SO2: " + airPollutionData.getSo2());
        System.out.println("PM2.5: " + airPollutionData.getPm25());
        System.out.println("PM10: " + airPollutionData.getPm10());
        System.out.println("NH3: " + airPollutionData.getNh3());
        System.out.println("City: " + airPollutionData.getCityName());

    }
}
