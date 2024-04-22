package Src.AppUI;

import java.util.Scanner;

import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;

public class TerminalUI {
    private static Scanner scanner = new Scanner(System.in);
    private static double latitude;
    private static double longitude;
    private static String city;

    public int RunF() {
        while (true) {
            addLocation();
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        while(true)
        {
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

            displayWeatherOptions();
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
        while (true) {
            System.out.println("\nWeather Options:");
            System.out.println("1. Show Current Weather");
            System.out.println("2. Show Weather Forecast");
            System.out.println("3. Exit");
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
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void showCurrentWeather() {
        try {
            CurrentWeatherAPI currentWeatherAPI = new CurrentWeatherAPI();
            currentWeatherAPI.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    public static void showWeatherForecast() {
        try {
            WeatherForecast5Days weatherForecast5Days = new WeatherForecast5Days();
            weatherForecast5Days.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching weather forecast: " + e.getMessage());
        }
    }


    public static void showAirPollutionData() {
        try {
            AirPollutionAPI airPollutionAPI = new AirPollutionAPI();
            airPollutionAPI.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching air pollution data: " + e.getMessage());
        }
    }

    public static void showCurrentWeatherbyCity() {
        try {
            CurrentWeatherAPI currentWeatherAPI = new CurrentWeatherAPI();
            currentWeatherAPI.APIcall(city);
        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    public static void showWeatherForecastbyCity() {
        try {
            WeatherForecast5Days weatherForecast5Days = new WeatherForecast5Days();
            weatherForecast5Days.APIcall(city);
        } catch (Exception e) {
            System.out.println("Error fetching weather forecast: " + e.getMessage());
        }
    }

}
