package Src.WeatherDataStorage.DBManager.interfaces;

import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;

public interface CurrWeatherCache {
    Boolean checkCurrentWeatherData(double latitude, double longitude);

    Boolean checkCurrentWeatherData(String cityName);

    void fetchCurrentWeatherData(CurrentWeatherAPIData currentWeather, double latitude, double longitude);

    void fetchCurrentWeatherData(CurrentWeatherAPIData currentWeather, String cityName);
}
