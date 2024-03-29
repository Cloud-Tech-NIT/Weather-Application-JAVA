package Src.AppUI;

import Src.OpenWeatherAPI.CurrentWeatherAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class mainscreenController {

    private final CurrentWeatherAPI weatherAPI;

    public mainscreenController() {
        this.weatherAPI = new CurrentWeatherAPI();
        this.weatherAPI.setController(this); // Set the controller reference
    }

    @FXML
    void initialize() {
        // Set default city (Lahore) on startup
        weatherAPI.APIcall("Lahore");
    }

    private Stage mainWindow;

    public void setMainWindow(Stage mainwindow) {
        this.mainWindow = mainwindow;
    }

    @FXML
    private Label tfAQI;

    @FXML
    private Label tfCityAndCountryName;

    @FXML
    private Label tfCurrentTemp;

    @FXML
    private Label tfCurrentWeatherConditon;

    @FXML
    private ImageView tfCurrentWeatherIcon;

    @FXML
    private Label tfDay1;

    @FXML
    private ImageView tfDay1Icon;

    @FXML
    private Label tfDay1Temp;

    @FXML
    private Label tfDay1WeatherConditon;

    @FXML
    private Label tfDay2;

    @FXML
    private ImageView tfDay2Icon;

    @FXML
    private Label tfDay2Temp;

    @FXML
    private Label tfDay2WeatherConditon;

    @FXML
    private Label tfDay3;

    @FXML
    private ImageView tfDay3Icon;

    @FXML
    private Label tfDay3Temp;

    @FXML
    private Label tfDay3WeatherConditon;

    @FXML
    private Label tfDay4;

    @FXML
    private ImageView tfDay4Icon;

    @FXML
    private Label tfDay4Temp;

    @FXML
    private Label tfDay4WeatherConditon;

    @FXML
    private Label tfDay5;

    @FXML
    private ImageView tfDay5Icon;

    @FXML
    private Label tfDay5Temp;

    @FXML
    private Label tfDay5WeatherConditon;

    @FXML
    private Label tfDayAndDate;

    @FXML
    private Label tfFeelsLike;

    @FXML
    private Label tfHumidity;

    @FXML
    private Label tfLatitude;

    @FXML
    private Label tfLongitude;

    @FXML
    private Label tfMaximumTemp;

    @FXML
    private Label tfMinimumTemp;

    @FXML
    private Label tfPressure;

    @FXML
    private Button tfSearchWeatherButton;

    @FXML
    private Label tfStandardTime;

    @FXML
    private Label tfSunrise;

    @FXML
    private Label tfWindspeed;

    @FXML
    void ExpandAirPollutionInfo(ActionEvent event) {

    }

    @FXML
    void SearchWeatherButton(ActionEvent event) {

    }

    public void updateUI(mainscreenController controller, String cityName, String countryName, double currentTemp,
            String weatherCondition,
            String weatherIconURL, double tempMin, double tempMax, int sunrise, int sunset, int pressure, int humidity,
            double windSpeed, double lat, double lon) {
        double temperatureInCelsius = currentTemp - 273.15;
        // Format the temperature to have one digit after the decimal point
        String formattedTemperature = String.format("%.1f", temperatureInCelsius);

        controller.tfCityAndCountryName.setText(cityName + ", " + countryName);
        controller.tfCurrentTemp.setText(String.valueOf(formattedTemperature) + 273.15 + " °C");
        controller.tfCurrentWeatherConditon.setText(weatherCondition);
        // Load weather icon from URL and set it to ImageView
        Image image = new Image(weatherIconURL);
        controller.tfCurrentWeatherIcon.setImage(image);
    }

}
