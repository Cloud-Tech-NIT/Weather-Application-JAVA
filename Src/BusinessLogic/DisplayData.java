package Src.BusinessLogic;

public interface DisplayData {
    // interface btw APIs and Terminal to display data--api callback to the func and
    // the terminal implements the func
    void displayAirPollutionData(double lat, double lon, long dt, int aqi,
            double co, double no, double no2,
            double o3, double so2, double pm2_5,
            double pm10, double nh3);

    void displayCurrentWeatherData(double lat, double lon, int weatherID, String weatherMain,
            String weatherDescription, double temp, double feelsLike,
            double tempMin, double tempMax, int pressure, int humidity,
            int visibility, double windSpeed, int windDeg,
            int cloudsAll, int dt, String country, int sunrise,
            int sunset, int timezone, String cityName);

    void displayWeatherForecast(double[][] data, String[] iconUrls, String[] weatherConditions, double lat, double lon,
            String cityName);

}