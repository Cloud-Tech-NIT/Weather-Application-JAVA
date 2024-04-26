package Src.BusinessLogic;

import java.sql.Connection;
import java.sql.SQLException;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;
import Src.WeatherDataStorage.DBCurrWeather;
import Src.WeatherDataStorage.DBweatherForecast;
import Src.WeatherDataStorage.DBAirPollDat;
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
 
  //DB objs
  private DBAirPollDat airpol=new DBAirPollDat();
  private DBweatherForecast frcst=new DBweatherForecast();
  private DBCurrWeather curr=new DBCurrWeather();

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
  //method to get and store for curr Weather

  //check Weather by coordinates
  public void CheckCurrWeatherCoord(double lat,double lon)
  { 
    if (curr.isDataPresentByLatLon(lat, lon))
     {
      curr.displayDataFromDatabaseByLatLon(lat,lon); 
    }
     else 
     {
      APIcall.SearchByCoord(lat, lon, CurrentWeather);
      if (CurrentWeather != null)
      { // Check if data is retrieved successfully
        curr.insertWeatherData(CurrentWeather);
      }
       else
      {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  //check curr weather by city
  public void CheckCurrWeatherCity(String City)
  { 
    if (curr.isDataPresentByCityName(City))
     {
      curr. displayDataFromDatabaseByCityName(City); // Assuming it returns data
    }
     else 
     {
      APIcall.SearchByCity(City, CurrentWeather);;
      if (CurrentWeather != null)
      { // Check if data is retrieved successfully
        curr.insertWeatherData(CurrentWeather);
      }
       else
      {
        // Handle API call error (e.g., display error message)
      }
    }
  }
  
  //check airpollution by City
  public void CheckAirPollCity(String City)
  { 
    if (airpol.isDataPresentByCityName(City))
     {
      airpol.displayDataFromDatabaseByCityName(City); // Assuming it returns data
    }
     else 
     {
      PollutionAPIcall.searchAirPollution(0, 0, City, AirPoll);     
       if (AirPoll != null)
      { 
        airpol.insertWeatherData(AirPoll);
      }
       else
      {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  //check airpollution by Coord
  public void CheckAirPollCoord(double lat,double lon)
  { 
    if (airpol.isDataPresentCoord(lat,lon))
     {
      airpol.displayDataFromDatabaseByLatLon(lat,lon); // Assuming it returns data
    }
     else 
     {
      PollutionAPIcall.searchAirPollution(lat, lon, null, AirPoll);     
       if (AirPoll != null)
      { 
        airpol.insertWeatherData(AirPoll);
      }
       else
      {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  public void CheckForecastCity(String City)
  {
    if (frcst.isDataPresentByCityName(Forecast))
     {
      frcst.displayDataFromDatabaseByCityName(Forecast); // Assuming it returns data
    }
     else 
     {
      WeatherAPIcall.SearchByCity(City, Forecast);    
       if (AirPoll != null)
      { 
        airpol.insertWeatherData(Forecast);
      }
       else
      {
        // Handle API call error (e.g., display error message)
      }
    }
  } 
  public void CheckForecastCoord(String City)
  {
    if (frcst.isDataPresentByCoord(Forecast))
     {
      frcst.displayDataFromDatabaseByCoord(Forecast); // Assuming it returns data
    }
     else 
     {
      WeatherAPIcall.SearchByCoord(0, 0, Forecast); 
       if (AirPoll != null)
      { 
        airpol.insertWeatherData(Forecast);
      }
       else
      {
        // Handle API call error (e.g., display error message)
      }
    }
  } 
  



 


  

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
