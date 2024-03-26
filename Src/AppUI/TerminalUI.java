package Src.AppUI;

import java.util.Scanner;

import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;

public class TerminalUI {
    private static Scanner scanner = new Scanner(System.in);

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
                    CheckNotifications();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nWeather Application Menu:");
        System.out.println("1. Add Location");
        System.out.println("2. Show Current Weather");
        System.out.println("3. Show Weather Forecast");
        System.out.println("4. Show Air Pollution Data");
        System.out.println("5. Check Notifications");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addLocation() {
        System.out.println("Adding location...");
    }

    private static void showCurrentWeather() {
        try {
            System.out.print("Enter latitude: ");
            double latitude = scanner.nextDouble();
            System.out.print("Enter longitude: ");
            double longitude = scanner.nextDouble();
    
            CurrentWeatherAPI currentWeatherAPI = new CurrentWeatherAPI();
            currentWeatherAPI.APIcall(latitude, longitude); // Call the API method
    
        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    private static void showWeatherForecast() {
        try {
            System.out.print("Enter latitude: ");
            double latitude = scanner.nextDouble();
            System.out.print("Enter longitude: ");
            double longitude = scanner.nextDouble();

            WeatherForecast5Days weatherForecast5Days = new WeatherForecast5Days();
            weatherForecast5Days.APIcall(latitude, longitude); // Call the API method

        } catch (Exception e) {
            System.out.println("Error fetching weather forecast: " + e.getMessage());
        }
    }

    private static void showAirPollutionData() {
        try {
            System.out.print("Enter latitude: ");
            double latitude = scanner.nextDouble();
            System.out.print("Enter longitude: ");
            double longitude = scanner.nextDouble();

            AirPollutionAPI airPollutionAPI = new AirPollutionAPI();
            airPollutionAPI.APIcall(latitude, longitude); // Call the API method

        } catch (Exception e) {
            System.out.println("Error fetching air pollution data: " + e.getMessage());
        }
    }

    private static void showPollutingGasesData() {
        try {
            System.out.print("Enter latitude: ");
            double latitude = scanner.nextDouble();
            System.out.print("Enter longitude: ");
            double longitude = scanner.nextDouble();

            AirPollutionAPI airPollutionAPI = new AirPollutionAPI();
            airPollutionAPI.APIcall(latitude, longitude);

        } catch (Exception e) {
            System.out.println("Error fetching air pollution data: " + e.getMessage());
        }
    }

    private static void CheckNotifications() {
        System.out.println("Generating weather notifications...");
    }

}
