package Src.BusinessLogic.TerminalUI;

import Src.AppUI.DisplayData;
import Src.AppUI.TerminalUI;
import Src.BusinessLogic.TUI;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;
import Src.WeatherDataStorage.DBManager.DBAirPollDat;
import Src.WeatherDataStorage.DBManager.DBCurrWeather;
import Src.WeatherDataStorage.DBManager.DBFrcst5Day;

public class TUI_DB implements TUI {

    DisplayData retrieve;
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

    public TUI_DB() {
        this.retrieve = new TerminalUI(this);
      }

    // check Weather by coordinates
    public void CheckCurrWeatherCoord(double lat, double lon) {
        if (curr.isDataPresentByLatLon(lat, lon)) {
            this.CurrentWeather = curr.displayDataFromDatabaseByLatLon(lat, lon);
            retrieve.RetriveCurrentWeatherData(CurrentWeather);
        } else {
            APIcall.SearchByCoord(lat, lon, CurrentWeather);
            if (CurrentWeather != null) { // Check if data is retrieved successfully
                retrieve.RetriveCurrentWeatherData(CurrentWeather);
                curr.insertWeatherData(CurrentWeather);
            } else {
                System.out.println("CurrentWeather is null");
            }
        }
    }

    // check curr weather by city
    public void CheckCurrWeatherCity(String City) {
        if (curr.isDataPresentByCityName(City)) {
            this.CurrentWeather = curr.displayDataFromDatabaseByCityName(City); // Assuming it returns data
            retrieve.RetriveCurrentWeatherData(CurrentWeather);

        } else {
            APIcall.SearchByCity(City, CurrentWeather);
            if (CurrentWeather != null) { // Check if data is retrieved successfully
                retrieve.RetriveCurrentWeatherData(CurrentWeather);
                curr.insertWeatherData(CurrentWeather);
            } else {
            }
        }
    }

    @Override
    public void getCurrentWeather(double latitude, double longitude, String cityName) {
        if (latitude != 0.0 && longitude != 0.0) {
            CheckCurrWeatherCoord(latitude, longitude);
        } else if (cityName != null && !cityName.isEmpty()) {
            CheckCurrWeatherCity(cityName);
        }
    }

    // check airpollution by Coord
    public void CheckAirPollCoord(double lat, double lon) {
        if (airpol.isDataPresentCoord(lat, lon)) {
            this.AirPoll = airpol.displayDataFromDatabaseByLatLon(lat, lon); // Assuming
            // it returns data
            retrieve.RetriveAirPollutionData(AirPoll);

        } else {
            APIcall.SearchByCoord(lat,lon,CurrentWeather);
            String city = CurrentWeather.getCityName();
            PollutionAPIcall.searchAirPollution(lat, lon, city, AirPoll);
            if (AirPoll != null) {
                retrieve.RetriveAirPollutionData(AirPoll);
                airpol.insertWeatherData(AirPoll);
            } else {
                // Handle API call error (e.g., display error message)
            }
        }
    }

    @Override
    public void getAirPollution(double latitude, double longitude, String cityName) {
        if (latitude != 0.0 && longitude != 0.0) {
            CheckAirPollCoord(latitude, longitude);
        } else if (cityName != null && !cityName.isEmpty()) {
            APIcall.SearchByCity(cityName, CurrentWeather);
            float lat = CurrentWeather.getLatitude();
            float lon = CurrentWeather.getLongitude();
            CheckAirPollCoord(lat, lon);

        }
    }

    public void CheckForecastCity(String City) {
        if (frcst.isDataPresentByCityName(City)) {
            this.Forecast = frcst.displayDataFromDatabaseByCityName(City); // Assuming it returns data
            retrieve.RetriveWeatherForecastData(Forecast);
        } else {
            WeatherAPIcall.SearchByCity(City, Forecast);
            if (Forecast != null) {
                retrieve.RetriveWeatherForecastData(Forecast);

                frcst.insertWeatherData(Forecast);
            } else {
                // Handle API call error (e.g., display error message)
            }
        }
    }

    public void CheckForecastCoord(double lat, double lon) {
        if (frcst.isDataPresentByLatLon(lat, lon)) {
            this.Forecast = frcst.displayDataFromDatabaseByLatLon(lat, lon); // Assuming it returns data
            retrieve.RetriveWeatherForecastData(Forecast);

        } else {
            WeatherAPIcall.SearchByCoord(lat, lon, Forecast);
            if (Forecast != null) {
                retrieve.RetriveWeatherForecastData(Forecast);
                frcst.insertWeatherData(Forecast);
            } else {
                // Handle API call error (e.g., display error message)
            }
        }
    }

    // Method to fetch weather forecast data
    @Override
    public void getWeatherForecast(double latitude, double longitude, String cityName) {
        if (latitude != 0.0 && longitude != 0.0) {
            CheckForecastCoord(latitude, longitude);
        } else if (cityName != null && !cityName.isEmpty()) {
            CheckForecastCity(cityName);

        }
    }

}
