package Src.WeatherDataStorage.DBManager.interfaces;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;

public interface ForecastCache {
    public Boolean checkWeatherForecastData(double latitude, double longitude);
    public Boolean checkWeatherForecastData(String cityName);
    public void fetchWeatherForecastData(WeatherForecastAPIData forecast, double latitude, double longitude);
    public void fetchWeatherForecastData(WeatherForecastAPIData forecast, String cityName);

    
} 