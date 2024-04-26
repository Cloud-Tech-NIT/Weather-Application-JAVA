package Src.AppUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Src.OpenWeatherAPI.AirPollutionAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Src.WeatherDataStorage.DBAirPoll;
import Src.WeatherDataStorage.DBweatherForecast;

public class Screen3Controller {
    private final DBAirPoll airPoll;
    @FXML
    private AnchorPane mainpane;

    @FXML
    private Text tfCO;

    @FXML
    private Text tfNH3;

    @FXML
    private Text tfNO;

    @FXML
    private Text tfNO2;

    @FXML
    private Text tfAirQualityIndex;

    @FXML
    private Text tfO3;

    @FXML
    private Text tfPM10;

    @FXML
    private Text tfPM2;

    @FXML
    private Text tfSO2;

    @FXML
    private Label tfCityName;

    private AirPollutionAPI airPollutionAPI;
    private Connection connection;

    public Screen3Controller() {
        this.airPollutionAPI = new AirPollutionAPI();
        this.airPoll = new DBAirPoll();
        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weather_Cache", "root", "4820");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize(double latitude, double longitude) {
        airPollutionAPI.setController(this); // Set the reference to this controller
        airPoll.setController(this);
        // check data in db
        boolean currpollexist = airPoll.isDataPresent(connection, latitude, longitude);

        if (currpollexist) {
            // alert messages were only so i can check if data is being fetched from db or
            // not
            // showAlert("Data Found", "Fetching data from the database...");

            airPoll.displayDataFromDatabase(connection, latitude, longitude);
        } else {
            // showAlert("Data not Found", "Fetching data from the API...");
            airPollutionAPI.APIcall(latitude, longitude); // Call the API
        }
    }

    // private void showAlert(String title, String message) {
    // Alert alert = new Alert(AlertType.INFORMATION);
    // alert.setTitle(title);
    // alert.setHeaderText(null);
    // alert.setContentText(message);
    // alert.showAndWait();
    // }

    public void setAirPollutionData(int aqi, double co, double nh3, double no, double no2, double o3, double pm10,
            double pm2_5, double so2) {
        tfAirQualityIndex.setText(Integer.toString(aqi));
        tfCO.setText(Double.toString(co));
        tfNH3.setText(Double.toString(nh3));
        tfNO.setText(Double.toString(no));
        tfNO2.setText(Double.toString(no2));
        tfO3.setText(Double.toString(o3));
        tfPM10.setText(Double.toString(pm10));
        tfPM2.setText(Double.toString(pm2_5));
        tfSO2.setText(Double.toString(so2));
    }
}
