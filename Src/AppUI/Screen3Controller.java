package Src.AppUI;

import Src.OpenWeatherAPI.AirPollutionAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Screen3Controller {

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

    public Screen3Controller() {
        this.airPollutionAPI = new AirPollutionAPI();
    }

    public void initialize(double latitude, double longitude) {
        airPollutionAPI.setController(this); // Set the reference to this controller
        airPollutionAPI.APIcall(latitude, longitude); // Call the API
    }

    public void setAirPollutionData(double co, double nh3, double no, double no2, double o3, double pm10,
            double pm2_5, double so2) {
        // tfAirQualityIndex.setText(Integer.toString(aqi));
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
