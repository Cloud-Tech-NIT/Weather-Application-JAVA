package Src.AppUI;

import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Src.BusinessLogic.TUI;
import Src.BusinessLogic.TUIFiller;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.OpenWeatherAPI.notificationInterface;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TerminalUI implements DisplayData {
    private static Scanner scanner = new Scanner(System.in);
    private static double latitude;
    private static double longitude;
    private static String city;
    private static TUI TUI = new TUIFiller();
    private notificationInterface notificationInterface;
    
    public double getLatitude() {
        return latitude;
    }

    // public void setLatitude(double latitude) {
    // this.latitude = latitude;
    // }

    public double getLongitude() {
        return longitude;
    }

    // public void setLongitude(double longitude) {
    // this.longitude = longitude;
    // }

    public String getCity() {
        return city;
    }

    // public void setCity(String city) {
    // this.city = city;
    // }
    public TerminalUI() {
        //TerminalUI.TUI = TUI;
    }

    public static void main(String[] args) {
        TerminalUI terminalUI = new TerminalUI();
        terminalUI.RunF();
    }

    public int RunF() {
        while (true) {
            addLocation();
        }
    }

    public static void addLocation() {

        System.out.println("Choose the method to add location:");
        System.out.println("1. By latitude and longitude");
        System.out.println("2. By city/country name");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addLocationByCoordinates();
                break;
            case 2:
                addLocationByCityName();
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

    }

    public static void addLocationByCoordinates() {
        System.out.print("Enter latitude: ");
        latitude = scanner.nextDouble();

        System.out.print("Enter longitude: ");
        longitude = scanner.nextDouble();
        System.out.println("Location added successfully.");

        // TUI.SearchByCoord(latitude, longitude);
        // Pass the terminalUI object to the displayWeatherOptions method
        displayWeatherOptions();
    }

    public static void addLocationByCityName() {

        System.out.print("Enter city name: ");
        city = scanner.nextLine().trim();

        if (!city.isEmpty()) {
            System.out.println("Location added successfully.");
            // TUI.SearchByCity(city);
            displayWeatherOptions();
        } else {
            System.out.println("Error: Unable to add location. Please check the city name and country code.");
        }
    }

    public static void displayWeatherOptions() {

        while (true) {
            System.out.println("\nWeather Options:");
            System.out.println("1. Show Current Weather");
            System.out.println("2. Show Weather Forecast");
            System.out.println("3. Show Air Pollution Data");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showCurrentWeather();
                    break;
                case 2:
                    showWeatherForecast();
                    break;
                case 3:
                    showAirPollutionData();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public static void showCurrentWeather() {
        try {
            TUI.getCurrentWeather(latitude, longitude, city);
        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    public static void showWeatherForecast() {
        try {
            TUI.getWeatherForecast(latitude, longitude, city);
        } catch (Exception e) {
            System.out.println("Error fetching weather forecast: " + e.getMessage());
        }
    }

    public static void showAirPollutionData() {
        try {

            TUI.getAirPollution(latitude, longitude, city);

            // airPollutionService.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching air pollution data: " + e.getMessage());
        }
    }
    @Override
    public void RetriveAirPollutionData(AirPollutionAPIData AirPoll) {
        // Retrieve air pollution data...
        // Extract data from AirPoll object
        double lat = AirPoll.getLatitude();
        double lon = AirPoll.getLongitude();
        String city = AirPoll.getCityName();
        long dt = AirPoll.getDt();
        int aqi = AirPoll.getAqi();
        double co = AirPoll.getCo();
        double no = AirPoll.getNo();
        double no2 = AirPoll.getNo2();
        double o3 = AirPoll.getO3();
        double so2 = AirPoll.getSo2();
        double pm2_5 = AirPoll.getPm25();
        double pm10 = AirPoll.getPm10();
        double nh3 = AirPoll.getNh3();
        // String city = AirPoll.getCityName();
        // Pass data to displayAirPollutionData method in the callback interface
        displayAirPollutionData(city, lat, lon, dt, aqi, co, no, no2, o3, so2, pm2_5, pm10, nh3);

        notificationInterface.generateNotification(aqi);


    }

    public void displayAirPollutionData(String city, double lat, double lon, long dt, int aqi,
            double co, double no, double no2,
            double o3, double so2, double pm2_5,
            double pm10, double nh3) {
        String Lat = String.format("%.2f", lat) + "°";
        String Lon = String.format("%.2f", lon) + "°";
        // Format date
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dt), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String Date = dateTime.format(formatter);
        System.out.println("Latitude: " + Lat);
        System.out.println("Longitude: " + Lon);
        System.out.println("City: " + city);
        System.out.println("Date Time: " + Date);
        System.out.println("Air Quality Index: " + aqi);
        System.out.println("Percentage of Polluting Gases:");
        System.out.println("CO: " + String.format("%.2f", co) + " ppm");
        System.out.println("NO: " + String.format("%.2f", no) + " ppm");
        System.out.println("NO2: " + String.format("%.2f", no2) + " ppm");
        System.out.println("O3: " + String.format("%.2f", o3) + " ppm");
        System.out.println("SO2: " + String.format("%.2f", so2) + " ppm");
        System.out.println("PM2.5: " + String.format("%.2f", pm2_5) + " µg/m^3");
        System.out.println("PM10: " + String.format("%.2f", pm10) + " µg/m^3");
        System.out.println("NH3: " + String.format("%.2f", nh3) + " ppm");
    }

    public void RetriveCurrentWeatherData(CurrentWeatherAPIData currentWeatherData) {
        // Retrieve current weather data
        // Extract data from CurrentWeatherAPIData object
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

        // Pass data to displayCurrentWeatherData method in the callback interface
        displayCurrentWeatherData(lat, lon, weatherID, weatherMain, weatherDescription,
                temp, feelsLike, tempMin, tempMax, pressure, humidity, visibility,
                windSpeed, windDeg, cloudsAll, dt, country, sunrise, sunset, timezone, cityName);
    }


    public void displayCurrentWeatherData(double lat, double lon, int weatherID, String weatherMain,
            String weatherDescription, double temp, double feelsLike,
            double tempMin, double tempMax, int pressure, int humidity,
            int visibility, double windSpeed, int windDeg,
            int cloudsAll, int dt, String country, int sunrise,
            int sunset, int timezone, String cityName) {

        // Convert sunrise and sunset from Unix timestamp to LocalDateTime
        LocalDateTime sunriseTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(sunrise),
                ZoneOffset.ofTotalSeconds(timezone));
        LocalDateTime sunsetTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(sunset),
                ZoneOffset.ofTotalSeconds(timezone));
        LocalDateTime dtTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(dt),
                ZoneOffset.ofTotalSeconds(timezone));
        LocalDateTime timezoneTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timezone),
                ZoneOffset.ofTotalSeconds(timezone));

        // Define the date time formatter for AM/PM forma
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");

        // Format sunrise and sunset times to AM/PM format
        String Sunrise = sunriseTime.format(formatter);
        String Sunset = sunsetTime.format(formatter);
        String DT = dtTime.format(formatter);
        String Timezone = timezoneTime.format(formatter);

        // Format numerical values to two decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Latitude: " + df.format(lat) + "°");
        System.out.println("Longitude: " + df.format(lon) + "°");
        System.out.println("City Name: " + cityName);
        System.out.println("Country: " + country);
        System.out.println("Weather Main: " + weatherMain);
        System.out.println("Weather Description: " + weatherDescription);
        System.out.println("Sunrise: " + Sunrise);
        System.out.println("Sunset: " + Sunset);
        System.out.println("Temperature: " + df.format(temp) + "°C");
        System.out.println("Feels Like: " + df.format(feelsLike) + "°C");
        System.out.println("Minimum Temperature: " + df.format(tempMin) + "°C");
        System.out.println("Maximum Temperature: " + df.format(tempMax) + "°C");
        System.out.println("Pressure: " + pressure + " hPa");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Visibility: " + visibility + " meters");
        System.out.println("Wind Speed: " + df.format(windSpeed) + " m/s");
        System.out.println("Wind Degree: " + windDeg + "°");
        System.out.println("Clouds: " + cloudsAll + "%");
        System.out.println("Data Time: " + DT);
        System.out.println("Timezone: " + Timezone);
    }
    @Override
    public void RetriveWeatherForecastData(WeatherForecastAPIData weatherForecastData) {
        // Extract data from weatherForecastData object
        double lat = weatherForecastData.getLatitude();
        double lon = weatherForecastData.getLongitude();
        double[][] data = weatherForecastData.getData();
        String[] weatherConditions = weatherForecastData.getWeatherCondition();
        String cityName = weatherForecastData.getCityName();

        // Call the displayWeatherForecast method in the terminalUI variable
        displayWeatherForecast(data, lat, lon, cityName, weatherConditions);
    }


    public void displayWeatherForecast(double[][] data, double lat,
            double lon, String cityName, String[] weatherConditions) {
        // Display weather forecast information in the terminal
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("---Weather Forecast for Next 5 Days---");
        System.out.println("Latitude: " + df.format(lat) + "°");
        System.out.println("Longitude: " + df.format(lon) + "°");
        System.out.println("City Name: " + cityName);
        for (int i = 0; i < data.length; i++) {
            System.out.println("DAY " + (i + 1));
            System.out.println("Temperature: " + data[i][0]);
            System.out.println("Minimum Temperature: " + data[i][1]);
            System.out.println("Maximum Temperature: " + data[i][2]);
            System.out.println("Pressure: " + data[i][3]);
            System.out.println("Humidity: " + data[i][4]);
            System.out.println("Weather Condition: " + weatherConditions[i]);
            System.out.println();
        }
    }

}
