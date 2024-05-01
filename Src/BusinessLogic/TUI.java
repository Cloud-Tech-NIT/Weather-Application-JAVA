package Src.BusinessLogic;

public interface TUI {
    
  void getCurrentWeather(double latitude, double longitude, String cityName);
  void getWeatherForecast(double latitude, double longitude, String cityName);
  void getAirPollution(double latitude, double longitude, String cityName);


}
