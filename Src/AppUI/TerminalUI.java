package Src.AppUI;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

// import com.google.gson.JsonArray;
// import com.google.gson.JsonObject;

import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;

public class TerminalUI {
    private static Scanner scanner = new Scanner(System.in);
    //private static List<Location> locations = new ArrayList<>();
    private static double latitude;
    private static double longitude;

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
    private static void addLocationByCoordinates() {
        System.out.print("Enter latitude: ");
        latitude = scanner.nextDouble();
        System.out.print("Enter longitude: ");
        longitude = scanner.nextDouble();
        System.out.println("Location added successfully.");
    }
    
    private static void addLocationByCityName() {
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

    // private static void chooseLocation() {
    //     if (locations.isEmpty()) {
    //         System.out.println("No locations added. Please add a location first.");
    //         return;
    //     }

    //     System.out.println("Locations:");
    //     for (int i = 0; i < locations.size(); i++) {
    //         System.out.println((i + 1) + ". " + locations.get(i));
    //     }

    //     System.out.print("Enter the number of the location to choose: ");
    //     int choice = scanner.nextInt();
    //     scanner.nextLine(); // Consume newline

    //     if (choice >= 1 && choice <= locations.size()) {
    //         Location chosenLocation = locations.get(choice - 1);
    //         latitude = chosenLocation.getLatitude();
    //         longitude = chosenLocation.getLongitude();
    //         System.out.println("Location chosen: " + chosenLocation);
    //     } else {
    //         System.out.println("Invalid choice. Please try again.");
    //     }
    // }

    private static void showCurrentWeather() {
        try {
            CurrentWeatherAPI currentWeatherAPI = new CurrentWeatherAPI();
            currentWeatherAPI.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching current weather data: " + e.getMessage());
        }
    }

    private static void showWeatherForecast() {
        try {
            WeatherForecast5Days weatherForecast5Days = new WeatherForecast5Days();
            weatherForecast5Days.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching weather forecast: " + e.getMessage());
        }
    }

    private static void showAirPollutionData() {
        try {
            AirPollutionAPI airPollutionAPI = new AirPollutionAPI();
            airPollutionAPI.APIcall(latitude, longitude);
        } catch (Exception e) {
            System.out.println("Error fetching air pollution data: " + e.getMessage());
        }
    }

    private static void CheckNotifications() {
        // try {
        //     // Create instances of the API classes
        //     CurrentWeatherAPI currentWeatherAPI = new CurrentWeatherAPI();
        //     AirPollutionAPI airPollutionAPI = new AirPollutionAPI();
    
        //     // Call API methods to get current weather and air pollution data
        //     JsonObject currentWeather = currentWeatherAPI.getCurrentWeather();
        //     JsonObject airPollutionData = airPollutionAPI.getAirPollutionData();
    
        //     // Check weather conditions
        //     String weatherCondition = currentWeather.getAsJsonArray("weather")
        //             .get(0).getAsJsonObject().get("main").getAsString();
        //     if (weatherCondition.equals("Rain") || weatherCondition.equals("Snow")) {
        //         System.out.println("Poor weather conditions detected! It's raining or snowing.");
        //     }
    
        //     // Check air quality
        //     if (airPollutionData != null && airPollutionData.has("list")) {
        //         JsonArray airQualityArray = airPollutionData.getAsJsonArray("list");
        //         if (airQualityArray.size() > 0) {
        //             JsonObject airQualityObject = airQualityArray.get(0).getAsJsonObject().getAsJsonObject("main");
        //             int airQualityIndex = airQualityObject.get("aqi").getAsInt();
        
        //             // Generate notifications for poor air quality
        //             if (airQualityIndex > 100) {
        //                 System.out.println("Poor air quality detected! Air Quality Index (AQI) is " + airQualityIndex + ".");
        //             }
        //         } else {
        //             System.out.println("No air pollution data available.");
        //         }
        //     } else {
        //         System.out.println("No air pollution data available.");
        //     }
        // } catch (Exception e) {
        //     System.out.println("Error checking notifications: " + e.getMessage());
        // }
    }

}
