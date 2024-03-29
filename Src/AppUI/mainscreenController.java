package Src.AppUI;

import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;

import Src.BusinessLogic.DUIFiller;
import Src.OpenWeatherAPI.CurrentWeatherAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Label tfSunset;

    @FXML
    private Label tfWindspeed;

    @FXML
    void ExpandAirPollutionInfo(ActionEvent event) {
        String lat = tfLatitude.getText();
        String lon = tfLongitude.getText();
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Src/AppUI/screen3.fxml"));
            Parent root = loader.load();
            Screen3Controller controller = loader.getController();
            controller.initialize(latitude, longitude); // Pass city name to Screen2Controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    void SearchWeatherButton(ActionEvent event) {

    }

    public void updateUI(mainscreenController controller, String cityName, String countryName, double currentTemp,
            String weatherCondition,
            String weatherIconURL, double lat, double lon, double feelsLike, int humidity, double tempMin,
            double tempMax,
            int sunrise, int sunset, int pressure, double windSpeed) {
        // Convert temperature units from Kelvin to Celsius
        double temperatureInCelsius = currentTemp - 273.15;
        double feelslikeInCelsius = feelsLike - 273.15;
        double mintemperatureInCelsius = tempMin - 273.15;
        double maxtemperatureInCelsius = tempMax - 273.15;
        // Format the temperature to have one digit after the decimal point
        String formattedTemperature = String.format("%.1f", temperatureInCelsius);
        String formattedFeelsLike = String.format("%.1f", feelslikeInCelsius);
        String formattedTempMin = String.format("%.1f", mintemperatureInCelsius);
        String formattedTempMax = String.format("%.1f", maxtemperatureInCelsius);
        String formattedWindSpeed = String.format("%.1f", windSpeed);

        // Set data to respective labels
        controller.tfCityAndCountryName.setText(cityName + ", " + countryName);
        controller.tfCurrentTemp.setText(formattedTemperature + " °C");
        controller.tfCurrentWeatherConditon.setText(weatherCondition);
        // Image image = new Image(weatherIconURL);
        // controller.tfCurrentWeatherIcon.setImage(image);
        controller.tfLatitude.setText(Double.toString(lat));
        controller.tfLongitude.setText(Double.toString(lon));
        controller.tfFeelsLike.setText(formattedFeelsLike + " °C");
        controller.tfHumidity.setText(Integer.toString(humidity) + " %");
        controller.tfMinimumTemp.setText(formattedTempMin + " °C");
        controller.tfMaximumTemp.setText(formattedTempMax + " °C");
        controller.tfSunrise.setText(formatTime(sunrise));
        controller.tfSunset.setText(formatTime(sunset));
        controller.tfPressure.setText(Integer.toString(pressure) + " hPa");
        controller.tfWindspeed.setText(formattedWindSpeed + " m/s");
    }

    // Method to format time in hh:mm format
    private String formatTime(int timeInSeconds) {
        // Convert time from seconds to milliseconds
        long millis = timeInSeconds * 1000L;
        // Create a Date object
        Date date = new Date(millis);
        // Create a SimpleDateFormat object with desired format
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        // Format the date
        return sdf.format(date);
    }
}
