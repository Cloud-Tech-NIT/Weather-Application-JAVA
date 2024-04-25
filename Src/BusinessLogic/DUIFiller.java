package Src.BusinessLogic;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;

public class DUIFiller {

  // Make the object of Desktop UI here

  // private DesktopUI=new DesktopUI; (Example)

  // Private Instances of Individual Temporary Data Storage
  private CurrentWeatherAPIData CurrentWeather = new CurrentWeatherAPIData();
  private WeatherForecastAPIData Forecast = new WeatherForecastAPIData();
  private AirPollutionAPIData AirPoll = new AirPollutionAPIData();

  // Private Instances of individual API calls
  private CurrentWeatherAPI APIcall = new CurrentWeatherAPI();
  private WeatherForecast5Days WeatherAPIcall = new WeatherForecast5Days();
  private AirPollutionAPI PollutionAPIcall = new AirPollutionAPI();

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
    WeatherAPIcall.SearchByCoord(lat, lon, Forecast);
    city = CurrentWeather.getCityName();
    PollutionAPIcall.searchAirPollution(lat, lon, city, AirPoll);
  }

  // Write Methods for getting data through DB and fill the CurrentWeather,
  // Forecast, AirPoll

  // (TEHREEM) ---

  public void getDataFromDB() {
    // Use this function if needed
  }

  public void DataPresent() {

    // Populate the UI by accessing the DB and filling the Objects by calling their
    // setters (Send the Objects of CurrentWeather,Forecast, AirPoll to DB
    // functions)

  }

  // ----

  public void DataNotPresent() {

    // if by coord then call SearchByCoord and populate the Desktop UI Object by the
    // individual variables of CurrentWeather, Forecast, AirPoll

    // if by City then call SearchByCity and populate the Desktop UI Object by the
    // individual variables of CurrentWeather, Forecast, AirPoll
  }

  public void Flow() {

    // Check For Data
    // getDataFromDB

    // ifNotPresent
    // this.DataNotPresent();

    // ifPresent
    // this.DataPresent();

  }

  public static void main(String[] args) {
    // DUIFiller DUI = new DUIFiller();
    // DUI.SearchByCoord(20.23, 19.34);
    // DUI.SearchByCity("lahore");

  }

}
