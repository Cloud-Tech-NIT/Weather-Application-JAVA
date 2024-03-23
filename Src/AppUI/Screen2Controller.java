package Src.AppUI;

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
    private Text tfFeelsLike2;

    @FXML
    private Text tfFeelsLike3;

    @FXML
    private Text tfMaximumTemp;

    @FXML
    private Text tfMinimumTemp;

    @FXML
    private Text tfSunriseTime;

    @FXML
    private Text tfSunsetTime;

    @FXML
    private Label tfcityname;

    @FXML
    private Text tfcuurentWeather;

    @FXML
    private Text tfdate;

    @FXML
    private Text tfstandardTime;

    public void initialize(String cityName) {
        tfcityname.setText(cityName);
        // You can perform other initialization tasks with the city name here
    }
}
