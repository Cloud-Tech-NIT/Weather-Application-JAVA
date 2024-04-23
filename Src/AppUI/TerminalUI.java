package Src.AppUI;

import java.util.Scanner;

import Src.BusinessLogic.DisplayData;
import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;
import Src.OpenWeatherAPI.InterfaceAPI;

public class TerminalUI implements DisplayData {
    private static Scanner scanner = new Scanner(System.in);
    private static double latitude;
    private static double longitude;
    private static String city;
    private static InterfaceAPI currentWeatherService;
    private static InterfaceAPI weatherForecastService;
    private static InterfaceAPI airPollutionService;



    public TerminalUI(InterfaceAPI currentWeatherService,
            InterfaceAPI weatherForecastService,
            InterfaceAPI airPollutionService) {
        this.currentWeatherService = currentWeatherService;
        this.weatherForecastService = weatherForecastService;
        this.airPollutionService = airPollutionService;

        if (airPollutionService instanceof AirPollutionAPI) {
            ((AirPollutionAPI) airPollutionService).setCallback(this);
        }

        if (currentWeatherService instanceof CurrentWeatherAPI) {
            ((CurrentWeatherAPI) currentWeatherService).setCallback(this);
        }

        if (weatherForecastService instanceof WeatherForecast5Days) {
            ((WeatherForecast5Days) weatherForecastService).setCallback(this);
        }
    }

    

    public static void main(String[] args) {
        InterfaceAPI currentWeatherService = new CurrentWeatherAPI(); // Example, replace with actual implementation
        InterfaceAPI weatherForecastService = new WeatherForecast5Days(); // Example, replace with actual
                                                                               // implementation
        InterfaceAPI airPollutionService = new AirPollutionAPI(); // Example, replace with actual implementation

        // Create TerminalUI instance
        TerminalUI terminalUI = new TerminalUI(currentWeatherService, weatherForecastService, airPollutionService);

        // Start the application
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

        displayWeatherOptions();
    }

    public static void addLocationByCityName() {
        System.out.print("Enter city name: ");
        city = scanner.nextLine().trim();

        if (!city.isEmpty()) {
            System.out.println("Location added successfully.");

            displayWeatherOptionsbyCity();
        } else {
            System.out.println("Error: Unable to add location. Please check the city name and country code.");
        }
    }

    public static void displayWeatherOptions() {
        boolean run = true;
        while (run) {
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
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void displayWeatherOptionsbyCity() {
        boolean runC = true;

        while (runC) {
            System.out.println("\nWeather Options:");
            System.out.println("1. Show Current Weather");
            System.out.println("2. Show Weather Forecast");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showCurrentWeatherbyCity();
                    break;
                case 2:
                    showWeatherForecastbyCity();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    runC = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void showCurrentWeather() {
        try {
            currentWeatherService.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    public static void showWeatherForecast() {
        try {
            weatherForecastService.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching weather forecast: " + e.getMessage());
        }
    }

    public static void showAirPollutionData() {
        try {
            airPollutionService.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching air pollution data: " + e.getMessage());
        }
    }

    public static void showCurrentWeatherbyCity() {
        try {
            currentWeatherService.APIcall(city);

        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    public static void showWeatherForecastbyCity() {
        try {
            weatherForecastService.APIcall(city);
        } catch (Exception e) {
            System.out.println("Error fetching weather forecast: " + e.getMessage());
        }
    }

    @Override
    public void displayAirPollutionData(double lat, double lon, long dt, int aqi,
            double co, double no, double no2,
            double o3, double so2, double pm2_5,
            double pm10, double nh3) {
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
    public void displayCurrentWeatherData(double lat, double lon, int weatherID, String weatherMain,
            String weatherDescription, double temp, double feelsLike,
            double tempMin, double tempMax, int pressure, int humidity,
            int visibility, double windSpeed, int windDeg,
            int cloudsAll, int dt, String country, int sunrise,
            int sunset, int timezone, String cityName) {
        // Display all weather information in the terminal
        System.out.println("Latitude: " + lat);
        System.out.println("Longitude: " + lon);
        System.out.println("Weather ID: " + weatherID);
        System.out.println("Weather Main: " + weatherMain);
        System.out.println("Weather Description: " + weatherDescription);
        System.out.println("Temperature: " + temp);
        System.out.println("Feels Like: " + feelsLike);
        System.out.println("Minimum Temperature: " + tempMin);
        System.out.println("Maximum Temperature: " + tempMax);
        System.out.println("Pressure: " + pressure);
        System.out.println("Humidity: " + humidity);
        System.out.println("Visibility: " + visibility);
        System.out.println("Wind Speed: " + windSpeed);
        System.out.println("Wind Degree: " + windDeg);
        System.out.println("Clouds: " + cloudsAll);
        System.out.println("Data Time: " + dt);
        System.out.println("Country: " + country);
        System.out.println("Sunrise: " + sunrise);
        System.out.println("Sunset: " + sunset);
        System.out.println("Timezone: " + timezone);
        System.out.println("City Name: " + cityName);
    }

    @Override
    public void displayWeatherForecast(double[][] data, String[] iconUrls, String[] weatherConditions, double lat,
            double lon, String cityName) {
        // Display weather forecast information in the terminal
        System.out.println("Weather Forecast for " + cityName + " (Latitude: " + lat + ", Longitude: " + lon + "):");
        for (int i = 0; i < data.length; i++) {
            System.out.println("DAY " + (i + 1));
            System.out.println("Temperature: " + data[i][0]);
            System.out.println("Minimum Temperature: " + data[i][1]);
            System.out.println("Maximum Temperature: " + data[i][2]);
            System.out.println("Pressure: " + data[i][3]);
            System.out.println("Humidity: " + data[i][4]);
            System.out.println("Icon URL: " + iconUrls[i]);
            System.out.println("Weather Condition: " + weatherConditions[i]);
            System.out.println();
        }
    }

}
