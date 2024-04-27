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
//import Src.WeatherDataStorage.DBweatherFrcst;
import Src.WeatherDataStorage.DBAirPollDat;
import Src.WeatherDataStorage.DBFrcst5Day;
import Src.AppUI.mainscreenController;
import Src.AppUI.Screen3Controller;
import Src.AppUI.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DUIFiller {

  // objects of Desktop UI
  private mainscreenController controller;
  private Screen3Controller airpoll_controller;

  public DUIFiller(mainscreenController controller) {
    this.controller = controller;
  }

  public DUIFiller(Screen3Controller controller) {
    this.airpoll_controller = controller;
  }

  // Private Instances of Individual Temporary Data Storage
  private CurrentWeatherAPIData CurrentWeather = new CurrentWeatherAPIData();
  private WeatherForecastAPIData Forecast = new WeatherForecastAPIData();
  private AirPollutionAPIData AirPoll = new AirPollutionAPIData();

  // Private Instances of individual API calls
  private CurrentWeatherAPI APIcall = new CurrentWeatherAPI();
  private WeatherForecast5Days WeatherAPIcall = new WeatherForecast5Days();
  private AirPollutionAPI PollutionAPIcall = new AirPollutionAPI();

  // DB objs
  private DBAirPollDat airpol = new DBAirPollDat();
  private DBFrcst5Day frcst = new DBFrcst5Day();
  private DBCurrWeather curr = new DBCurrWeather();

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
  // method to get and store for curr Weather
  private void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  // check Weather by coordinates
  public void CheckCurrWeatherCoord(double lat, double lon) {
    if (curr.isDataPresentByLatLon(lat, lon)) {
      curr.displayDataFromDatabaseByLatLon(lat, lon);
    } else {
      APIcall.SearchByCoord(lat, lon, CurrentWeather);
      if (CurrentWeather != null) { // Check if data is retrieved successfully
        curr.insertWeatherData(CurrentWeather);
      } else {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  // check curr weather by city
  public void CheckCurrWeatherCity(String City) {
    if (curr.isDataPresentByCityName(City)) {
      showAlert("Data Found for both current weather and weather forecast",
          "Fetching data from the database...");
      this.CurrentWeather = curr.displayDataFromDatabaseByCityName(City); // Assuming it returns data
      controller.updateUI(controller, CurrentWeather);

    } else {
      APIcall.SearchByCity(City, CurrentWeather);
      if (CurrentWeather != null) { // Check if data is retrieved successfully
        controller.updateUI(controller, CurrentWeather);
        curr.insertWeatherData(CurrentWeather);
      } else {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  // check airpollution by City
  public void CheckAirPollCity(String City) {
    if (airpol.isDataPresentByCityName(City)) {
      airpol.displayDataFromDatabaseByCityName(City); // Assuming it returns data
    } else {
      PollutionAPIcall.searchAirPollution(0, 0, City, AirPoll);
      if (AirPoll != null) {
        airpol.insertWeatherData(AirPoll);
      } else {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  // check airpollution by Coord
  public void CheckAirPollCoord(double lat, double lon) {
    if (airpol.isDataPresentCoord(lat, lon)) {
      this.AirPoll = airpol.displayDataFromDatabaseByLatLon(lat, lon); // Assuming it returns data
      airpoll_controller.setAirPollutionData(AirPoll);

    } else {
      PollutionAPIcall.searchAirPollution(lat, lon, null, AirPoll);
      if (AirPoll != null) {
        airpoll_controller.setAirPollutionData(AirPoll);
        airpol.insertWeatherData(AirPoll);
      } else {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  public void CheckForecastCity(String City) {
    if (frcst.isDataPresentByCityName(City)) {
      frcst.displayDataFromDatabaseByCityName(City); // Assuming it returns data
    } else {
      WeatherAPIcall.SearchByCity(City, Forecast);
      if (Forecast != null) {
        frcst.insertWeatherData(Forecast);
      } else {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  public void CheckForecastCoord(double lat, double lon) {
    if (frcst.isDataPresentByLatLon(lat, lon)) {
      frcst.displayDataFromDatabaseByLatLon(lat, lon); // Assuming it returns data
    } else {
      WeatherAPIcall.SearchByCoord(0, 0, Forecast);
      if (Forecast != null) {
        frcst.insertWeatherData(Forecast);
      } else {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  public void runGUI() {
    String[] args = {};
    App.main(args);
  }

  public void Flow(String city) {

    // Check For Data
    // getDataFromDB
    CheckCurrWeatherCity(city);
    CheckAirPollCity(city);
    // CheckForecastCity(city);
    // ifNotPresent
    // this.DataNotPresent();

    // ifPresent
    // this.DataPresent();
  }

  public void Flow(double lat, double longi) {
    CheckAirPollCoord(lat, longi);
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

  public static void main(String[] args) {
    // DUIFiller DUI = new DUIFiller();
    // // DUI.SearchByCoord(20.23, 19.34);
    // // DUI.SearchByCity("lahore");
    // DUI.runGUI();
  }

}
