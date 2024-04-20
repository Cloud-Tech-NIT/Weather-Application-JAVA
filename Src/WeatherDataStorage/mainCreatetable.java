package Src.WeatherDataStorage;

public class mainCreatetable {
    public static void main(String[] args) {
        createTables tablesCreator = new createTables();

        // Create tables for current weather data
        tablesCreator.Table_CurrentWeatherData();

        // Create tables for air pollution data
        tablesCreator.Table_Air_Pollution();

        // Create tables for weather forecast data
        tablesCreator.Table_weather_Forecast();
    }
}
