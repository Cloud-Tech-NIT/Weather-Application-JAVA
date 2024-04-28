package Src.BusinessLogic;

import Src.AppUI.TerminalUI;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;
import Src.WeatherDataStorage.CacheManager;
import Src.WeatherDataStorage.DataHandlingTxT;
import Src.WeatherDataStorage.WeatherDataTxtStorage;

public class TUIFiller {

  // Make the object of Terminal UI herexxxc

  private TerminalUI terminalUI;

  // private TerminalUI Terminal = new TerminalUI(); //(Example)

  // Private Instances of Individual Temporary Data Storage
  private CurrentWeatherAPIData CurrentWeather = new CurrentWeatherAPIData();
  private WeatherForecastAPIData Forecast = new WeatherForecastAPIData();
  private AirPollutionAPIData AirPoll = new AirPollutionAPIData();

  // Private Instances of individual API calls
  private CurrentWeatherAPI APIcall = new CurrentWeatherAPI();
  private WeatherForecast5Days WeatherAPIcall = new WeatherForecast5Days();
  private AirPollutionAPI PollutionAPIcall = new AirPollutionAPI();

  // instance of the interface of CacheManager to access DB
  private CacheManager cache = new DataHandlingTxT();

  public TUIFiller() {
    terminalUI = new TerminalUI(this);
    WeatherDataTxtStorage.deleteOldData();

  }

  public void getCurrentWeather(double latitude, double longitude, String cityName) {
    
    if (!(cache.checkCurrentWeatherData(latitude, longitude) || cache.checkCurrentWeatherData(cityName))) {
      if (latitude != 0 && longitude != 0) {
        APIcall.SearchByCoord(latitude, longitude, CurrentWeather);
      } else if (cityName != null && !cityName.isEmpty()) {
        APIcall.SearchByCity(cityName, CurrentWeather);
      }
      WeatherDataTxtStorage.storeCurrentWeatherData(CurrentWeather);
    }
    else
    {

      if (latitude != 0 && longitude != 0) {
        cache.fetchCurrentWeatherData(CurrentWeather, latitude, longitude);
      } else if (cityName != null && !cityName.isEmpty()) {
        cache.fetchCurrentWeatherData(CurrentWeather,cityName);
      }
    }
    terminalUI.RetriveCurrentWeatherData(CurrentWeather);
  }

  // Method to fetch weather forecast data
  public void getWeatherForecast(double latitude, double longitude, String cityName) {
   if (!(cache.checkWeatherForecastData(latitude, longitude) || cache.checkWeatherForecastData(cityName))) {
      if (latitude != 0 && longitude != 0) {
        WeatherAPIcall.SearchByCoord(latitude, longitude, Forecast);
      } else if (cityName != null && !cityName.isEmpty()) {
        WeatherAPIcall.SearchByCity(cityName, Forecast);
      }
      WeatherDataTxtStorage.storeWeatherForecastData(Forecast);
    }
    else{
      
      if (latitude != 0 && longitude != 0) {
        cache.fetchWeatherForecastData(Forecast, latitude, longitude);
      } else if (cityName != null && !cityName.isEmpty()) {
        cache.fetchWeatherForecastData(Forecast,cityName);
      }
    }
    terminalUI.RetriveWeatherForecastData(Forecast);

  }

  public void getAirPollution(double latitude, double longitude, String cityName) {
    if (!cache.checkAirPollutionData(latitude, longitude)) {
      if (latitude != 0 && longitude != 0) {
        PollutionAPIcall.searchAirPollution(latitude, longitude, cityName, AirPoll);
        WeatherDataTxtStorage.storeAirPollutionData(AirPoll);

      }
    } else {
      cache.fetchAirPollutionData(AirPoll, latitude, longitude);
    }
    // Use TerminalUI variable to call retrieve function and pass the data
    terminalUI.RetriveAirPollutionData(AirPoll);
  }

  // Method For Searching By City
  public void SearchByCity(String CityName) {
    float lat;
    float lon;
    APIcall.SearchByCity(CityName, CurrentWeather);
    WeatherAPIcall.SearchByCity(CityName, Forecast);
    lat = CurrentWeather.getLatitude();
    lon = CurrentWeather.getLongitude();
    PollutionAPIcall.searchAirPollution(lat, lon, CityName, AirPoll);

  }

  // Method for Searching By Coord
  public void SearchByCoord(double lat, double lon) {
    String city;
    APIcall.SearchByCoord(lat, lon, CurrentWeather);
    city = CurrentWeather.getCityName();
    WeatherAPIcall.SearchByCoord(lat, lon, Forecast);
    PollutionAPIcall.searchAirPollution(lat, lon, city, AirPoll);
    System.out.println(city);

  }

  // Write Methods for getting data through txt and fill the CurrentWeather,
  // Forecast, AirPoll Objects

  public void getDataFromDB() {
    // Use this function if needed for txt storage
  }

  public void DataPresent() {

    // Populate the UI by accessing the DB (txtFile) and filling the Objects by
    // calling their
    // setters (Send the Objects of CurrentWeather,Forecast, AirPoll to DB
    // functions)

  }

  public void DataNotPresent() {

    // if by coord then call SearchByCoord and populate the Terminal UI Object by
    // the
    // individual variables of CurrentWeather, Forecast, AirPoll

    // if by City then call SearchByCity and populate the Terminal UI Object by the
    // individual variables of CurrentWeather, Forecast, AirPoll
  }

  public void Flow(String city) {

    // boolean DataExist = cache.checkData(city);
    // if (!DataExist) {
    // cache.FetchData("CurrentWeatherData.txt", 34, 56);
    // }
    // Check For Data
    // getDataFromDB

    // ifNotPresent
    // this.DataNotPresent();

    // ifPresent
    // this.DataPresent();

  }

  public static void main(String[] args) {
    TUIFiller tuiFiller = new TUIFiller();

    // Specify the latitude, longitude, and city name
    double latitude = 31.5;
    double longitude = 74.35;
    String cityName = "";
    // Call the method to get air pollution data
    //tuiFiller.getAirPollution(latitude, longitude, cityName);
    tuiFiller.getWeatherForecast(latitude, longitude,cityName);
    //tuiFiller.getCurrentWeather(latitude,longitude,cityName);
    //tuiFiller.getCurrentWeather(latitude,longitude,cityName);


    // Print or handle the air pollution data as needed
    // System.out.println("Air Pollution Data: " + airPollutionData.getCo());
  }

}
