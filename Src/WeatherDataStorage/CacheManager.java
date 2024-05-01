
package Src.WeatherDataStorage;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;

/**
 * DataHandlingInterface
 */
public interface CacheManager {
  public Boolean checkAirPollutionData(double latitude, double longitude);

  public Boolean checkCurrentWeatherData(double latitude, double longitude);

  public Boolean checkWeatherForecastData(double latitude, double longitude);

  public Boolean checkCurrentWeatherData(String cityName);

  public Boolean checkWeatherForecastData(String cityName);
  public Boolean checkAirPollutionData(String cityName);

  public void fetchAirPollutionData(AirPollutionAPIData AirPoll, double latitude, double longitude);

  public void fetchCurrentWeatherData(CurrentWeatherAPIData CurrentWeather, double latitude, double longitude);

  public void fetchWeatherForecastData(WeatherForecastAPIData Forecast,double latitude, double longitude);

  public void fetchCurrentWeatherData(CurrentWeatherAPIData CurrentWeather, String cityName);

  public void fetchWeatherForecastData(WeatherForecastAPIData Forecast,String cityName);
  public void fetchAirPollutionData(AirPollutionAPIData AirPoll,String cityName);


}