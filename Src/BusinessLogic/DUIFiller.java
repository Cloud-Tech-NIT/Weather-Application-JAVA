package Src.BusinessLogic;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;
import Src.WeatherDataStorage.DBManager.DBAirPollDat;
import Src.WeatherDataStorage.DBManager.DBCurrWeather;
import Src.WeatherDataStorage.DBManager.DBFrcst5Day;
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
      this.CurrentWeather = curr.displayDataFromDatabaseByLatLon(lat, lon);
      controller.updateUI(controller, CurrentWeather);
    } else {
      APIcall.SearchByCoord(lat, lon, CurrentWeather);
      if (CurrentWeather != null) { // Check if data is retrieved successfully
        controller.updateUI(controller, CurrentWeather);
        curr.insertWeatherData(CurrentWeather);
      } else {
      }
    }
  }

  // check curr weather by city
  public void CheckCurrWeatherCity(String City) {
    if (curr.isDataPresentByCityName(City)) {
      this.CurrentWeather = curr.displayDataFromDatabaseByCityName(City); // Assuming it returns data
      controller.updateUI(controller, CurrentWeather);

    } else {
      APIcall.SearchByCity(City, CurrentWeather);
      if (CurrentWeather != null) { // Check if data is retrieved successfully
        controller.updateUI(controller, CurrentWeather);
        curr.insertWeatherData(CurrentWeather);
      } else {
      }
    }
  }

  // // check airpollution by City
  // public void CheckAirPollCity(String City) {
  // if (airpol.isDataPresentByCityName(City)) {
  // this.AirPoll = airpol.displayDataFromDatabaseByCityName(City); // Assuming it
  // returns data
  // airpoll_controller.setAirPollutionData(AirPoll);

  // } else {
  // PollutionAPIcall.searchAirPollution(0, 0, City, AirPoll);
  // if (AirPoll != null) {
  // airpoll_controller.setAirPollutionData(AirPoll);
  // airpol.insertWeatherData(AirPoll);
  // } else {
  // // Handle API call error (e.g., display error message)
  // }
  // }
  // }

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
      this.Forecast = frcst.displayDataFromDatabaseByCityName(City); // Assuming it returns data
      controller.updateForecast(Forecast);
    } else {
      WeatherAPIcall.SearchByCity(City, Forecast);
      if (Forecast != null) {
        controller.updateForecast(Forecast);
        frcst.insertWeatherData(Forecast);
      } else {
        // Handle API call error (e.g., display error message)
      }
    }
  }

  public void CheckForecastCoord(double lat, double lon) {
    if (frcst.isDataPresentByLatLon(lat, lon)) {
      this.Forecast = frcst.displayDataFromDatabaseByLatLon(lat, lon); // Assuming it returns data
      controller.updateForecast(Forecast);
    } else {
      WeatherAPIcall.SearchByCoord(0, 0, Forecast);
      if (Forecast != null) {
        controller.updateForecast(Forecast);
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
    CheckCurrWeatherCity(city);
    CheckForecastCity(city);
  }

  public void Flow(double lat, double longi) {
    CheckCurrWeatherCoord(lat, longi);
    CheckForecastCoord(lat, longi);
  }

  public static void main(String[] args) {
    // DUIFiller DUI = new DUIFiller();
    // // DUI.SearchByCoord(20.23, 19.34);
    // // DUI.SearchByCity("lahore");
    // DUI.runGUI();
  }

}
