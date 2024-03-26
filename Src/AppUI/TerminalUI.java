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
                    showBasicInformation();
                    break;
                case 4:
                    showSunriseSunset();
                    break;
                case 5:
                    showWeatherForecast();
                    break;
                case 6:
                    showAirPollutionData();
                    break;
                case 7:
                    showPollutingGasesData();
                    break;
                case 8:
                    generateWeatherNotifications();
                    break;
                case 9:
                    generateAirQualityNotifications();
                    break;
                case 10:
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
        System.out.println("3. Show Basic Information");
        System.out.println("4. Show Sunrise and Sunset");
        System.out.println("5. Show Weather Forecast");
        System.out.println("6. Show Air Pollution Data");
        System.out.println("7. Show Polluting Gases Data");
        System.out.println("8. Generate Weather Notifications");
        System.out.println("9. Generate Air Quality Notifications");
        System.out.println("10. Exit");
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
            currentWeatherAPI.getData(latitude, longitude); // Call the API method
    
        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    private static void showBasicInformation() {
        System.out.println("Showing basic information...");
    }

    private static void showSunriseSunset() {
        System.out.println("Showing sunrise and sunset...");
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

    private static void generateWeatherNotifications() {
        System.out.println("Generating weather notifications...");
    }

    private static void generateAirQualityNotifications() {
        System.out.println("Generating air quality notifications...");
    }
}
