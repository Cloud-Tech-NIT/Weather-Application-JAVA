
package Src.AppUI;

import com.google.gson.JsonObject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Screen2Controller {

    @FXML
    private AnchorPane LeftPane;

    @FXML
    private TableColumn<?, ?> day1;

    @FXML
    private TableColumn<?, ?> day2;

    @FXML
    private TableColumn<?, ?> day3;

    @FXML
    private TableColumn<?, ?> day4;

    @FXML
    private TableColumn<?, ?> day5;

    @FXML
    private AnchorPane rightpane;

    @FXML
    private Text tfFeelsLike;

    @FXML
    private Text tfMaximumTemp;

    @FXML
    private Text tfMinimumTemp;

    @FXML
    private Text tfSunriseTime;

    @FXML
    private Text tfSunsetTime;

    @FXML
    private Text tfWeatherconditon;

    @FXML
    private Text tfcitylatitude;

    @FXML
    private Text tfcitylongitude;

    @FXML
    private Label tfcityname;

    @FXML
    private Text tfcurrentdate;

    @FXML
    private Text tfcuurentWeather;

    @FXML
    private Text tfhumidity;

    @FXML
    private Text tfpressure;

    @FXML
    private Text tfstandardTime;

    @FXML
    private Text tfweatherdescription;

    @FXML
    private Text tfwindspeed;

    public void initialize(String cityName) {
        tfcityname.setText(cityName);
        // You can perform other initialization tasks with the city name here
    }

    public static void updateWeatherData(Screen2Controller controller, double temperature, double feelsLike,
            int humidity, double tempMin, double tempMax, int pressure, double windspeed, int sunrise, int sunset) {
        // Update UI elements with weather data
        controller.tfcuurentWeather.setText(Double.toString(temperature));
        controller.tfFeelsLike.setText(Double.toString(feelsLike));
        controller.tfhumidity.setText(Integer.toString(humidity));
        controller.tfMinimumTemp.setText(Double.toString(tempMin));
        controller.tfMaximumTemp.setText(Double.toString(tempMax));
        controller.tfpressure.setText(Integer.toString(pressure));
        controller.tfwindspeed.setText(Double.toString(windspeed));
        controller.tfSunriseTime.setText(Integer.toString(sunrise));
        controller.tfSunsetTime.setText(Integer.toString(sunset));
    }

    public void initializeWeatherData(JsonObject weatherData) {
        // Extract required data from the weatherData JsonObject
        double temperature = weatherData.get("main").getAsJsonObject().get("temp").getAsDouble();
        double feelsLike = weatherData.get("main").getAsJsonObject().get("feels_like").getAsDouble();
        int humidity = weatherData.get("main").getAsJsonObject().get("humidity").getAsInt();
        double tempMin = weatherData.get("main").getAsJsonObject().get("temp_min").getAsDouble();
        double tempMax = weatherData.get("main").getAsJsonObject().get("temp_max").getAsDouble();
        int pressure = weatherData.get("main").getAsJsonObject().get("pressure").getAsInt();
        double windSpeed = weatherData.get("wind").getAsJsonObject().get("speed").getAsDouble();
        int sunrise = weatherData.get("sys").getAsJsonObject().get("sunrise").getAsInt();
        int sunset = weatherData.get("sys").getAsJsonObject().get("sunset").getAsInt();

        // Update UI elements with weather data
        Screen2Controller.updateWeatherData(this, temperature, feelsLike, humidity, tempMin, tempMax, pressure,
                windSpeed, sunrise, sunset);
    }

}
