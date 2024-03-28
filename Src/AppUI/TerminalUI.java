package Src.AppUI;

import java.util.Scanner;

import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;

public class TerminalUI {
    private static Scanner scanner = new Scanner(System.in);
    private static double latitude;
    private static double longitude;

    public int RunF() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addLocation();
                    break;
                case 2:
                    showCurrentWeather();
                    break;
                case 3:
                    showWeatherForecast();
                    break;
                case 4:
                    showAirPollutionData();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addLocation();
                    break;
                case 2:
                    showCurrentWeather();
                    break;
                case 3:
                    showWeatherForecast();
                    break;
                case 4:
                    showAirPollutionData();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void displayMenu() {
        System.out.println("\nWeather Application Menu:");
        System.out.println("1. Add Location");
        System.out.println("2. Show Current Weather");
        System.out.println("3. Show Weather Forecast");
        System.out.println("4. Show Air Pollution Data");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void addLocation() {
        System.out.println("Choose the method to add location:");
        System.out.println("1. By latitude and longitude");
        System.out.println("2. By city/country name");
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
    }

    public static void addLocationByCityName() {
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine().trim();
        System.out.print("Enter country (optional, press Enter to skip): ");
        String countryCode = scanner.nextLine().trim();

        // Geocoding API to get coordinates from city name

        latitude = 0.0; // Replace with actual latitude obtained from geocoding API
        longitude = 0.0; // Replace with actual longitude obtained from geocoding API

        if (latitude != 0.0 && longitude != 0.0) {
            System.out.println("Location added successfully.");
        } else {
            System.out.println("Error: Unable to add location. Please check the city name and country code.");
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

}
