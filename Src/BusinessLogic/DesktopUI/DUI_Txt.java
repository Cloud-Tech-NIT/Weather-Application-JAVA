package Src.BusinessLogic.DesktopUI;

import Src.AppUI.DisplayData;
import Src.AppUI.Screen3Controller;
import Src.AppUI.TerminalUI;
import Src.AppUI.mainscreen2controller;
import Src.AppUI.mainscreenController;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.OpenWeatherAPI.AirPollutionAPI;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import Src.OpenWeatherAPI.WeatherForecast5Days;
import Src.WeatherDataStorage.CacheManager;

import Src.WeatherDataStorage.StoreTxt;
import Src.WeatherDataStorage.dataHandling.DataHandlingTxT;
import Src.WeatherDataStorage.dataHandling.WeatherDataTxtStorage;

public class DUI_Txt {

    // objects of Desktop UI
    private mainscreenController controller;
    private Screen3Controller airpoll_controller;
    private mainscreen2controller controller2;

    public DUI_Txt(mainscreenController controller) {
        this.controller = controller;
    }

    public DUI_Txt(mainscreen2controller controller2) {
        this.controller2 = controller2;
    }

    public DUI_Txt(Screen3Controller controller) {
        this.airpoll_controller = controller;
    }

    public void setscreen3controller(Screen3Controller controller) {
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

    // instance of the interface of CacheManager to access DB
    private CacheManager cache = new DataHandlingTxT();
    private StoreTxt store = new WeatherDataTxtStorage();

    public DUI_Txt() {
        // terminalUI = new TerminalUI();
        store.deleteOldData();

    }

    public void getCurrentWeather(double latitude, double longitude, String cityName) {

        if (!(cache.checkCurrentWeatherData(latitude, longitude) || cache.checkCurrentWeatherData(cityName))) {
            if (latitude != 0 && longitude != 0) {
                APIcall.SearchByCoord(latitude, longitude, CurrentWeather);
            } else if (cityName != null && !cityName.isEmpty()) {
                APIcall.SearchByCity(cityName, CurrentWeather);
            }
            store.storeCurrentWeatherData(CurrentWeather);
        } else {

            if (latitude != 0 && longitude != 0) {
                cache.fetchCurrentWeatherData(CurrentWeather, latitude, longitude);
            } else if (cityName != null && !cityName.isEmpty()) {
                cache.fetchCurrentWeatherData(CurrentWeather, cityName);
            }
        }
        controller2.updateUI(controller2, CurrentWeather);

    }

    // Method to fetch weather forecast data
    public void getWeatherForecast(double latitude, double longitude, String cityName) {
        if (!(cache.checkWeatherForecastData(latitude, longitude) || cache.checkWeatherForecastData(cityName))) {
            if (latitude != 0 && longitude != 0) {
                WeatherAPIcall.SearchByCoord(latitude, longitude, Forecast);
            } else if (cityName != null && !cityName.isEmpty()) {
                WeatherAPIcall.SearchByCity(cityName, Forecast);
            }
            store.storeWeatherForecastData(Forecast);
        } else {

            if (latitude != 0 && longitude != 0) {
                cache.fetchWeatherForecastData(Forecast, latitude, longitude);
            } else if (cityName != null && !cityName.isEmpty()) {
                cache.fetchWeatherForecastData(Forecast, cityName);
            }
        }
        controller2.updateForecast(Forecast);

    }

    public void getAirPollution(double latitude, double longitude, String cityName) {
        if (!(cache.checkAirPollutionData(latitude, longitude) || cache.checkAirPollutionData(cityName))) {
            if (latitude != 0 && longitude != 0) {
                APIcall.SearchByCoord(latitude, longitude, CurrentWeather);
                String city = CurrentWeather.getCityName();
                PollutionAPIcall.searchAirPollution(latitude, longitude, city, AirPoll);

            } else if (cityName != null && !cityName.isEmpty()) {
                APIcall.SearchByCity(cityName, CurrentWeather);
                float lat = CurrentWeather.getLatitude();
                float lon = CurrentWeather.getLongitude();
                PollutionAPIcall.searchAirPollution(lat, lon, cityName, AirPoll);
            }
            store.storeAirPollutionData(AirPoll);

        } else {
            if (latitude != 0 && longitude != 0) {
                cache.fetchAirPollutionData(AirPoll, latitude, longitude);
            } else if (cityName != null && !cityName.isEmpty()) {
                cache.fetchAirPollutionData(AirPoll, cityName);
            }
        }
        // Use TerminalUI variable to call retrieve function and pass the data
        airpoll_controller.setAirPollutionData(AirPoll);
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

    public void Flow(String city) {
        getCurrentWeather(0, 0, city);
        getWeatherForecast(0, 0, city);
    }

    public void Flow(double lat, double longi) {
        getCurrentWeather(lat, longi, null);
        getWeatherForecast(lat, longi, null);
    }

    public static void main(String[] args) {
        DUI_Txt tuiFiller = new DUI_Txt();

        // Specify the latitude, longitude, and city name
        double latitude = 0.0;
        double longitude = 0.0;
        String cityName = "Karachi";
        // Call the method to get air pollution data
        tuiFiller.getAirPollution(latitude, longitude, cityName);
        // tuiFiller.getWeatherForecast(latitude, longitude,cityName);
        // tuiFiller.getCurrentWeather(latitude,longitude,cityName);
        // tuiFiller.getCurrentWeather(latitude,longitude,cityName);

        // Print or handle the air pollution data as needed
        // System.out.println("Air Pollution Data: " + airPollutionData.getCo());
    }
}
