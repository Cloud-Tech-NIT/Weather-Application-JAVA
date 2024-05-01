package Src.AppUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Src.OpenWeatherAPI.AirPollutionAPI;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.DUIFiller;
import Src.BusinessLogic.DesktopUI.DUI_DB;
import Src.BusinessLogic.DesktopUI.DUI_Txt;

public class Screen3Controller implements screen3interface {
    // public String database_used = "Txt";

    // private DUI_DB executeflow_sql;
    // private DUI_Txt executeflow_txt;

    // public void setdb(String db) {
    // this.database_used = db;
    // }

    // public Screen3Controller(String db) {
    // this.database_used = db;
    // if ("SQL".equals(database_used)) {
    // this.executeflow_sql = new DUI_DB(this);
    // } else if ("Txt".equals(database_used)) {
    // this.executeflow_txt = new DUI_Txt(this);
    // }
    // }

    public Screen3Controller() {

    }

    public void initialize(double latitude, double longitude, String db, DUI_DB executeflow_sql,
            DUI_Txt executeflow_txt) {
        if ("SQL".equals(db) && executeflow_sql != null) {
            executeflow_sql.setscreen3controller(this);
            executeflow_sql.CheckAirPollCoord(latitude, longitude);
        } else if ("Txt".equals(db) && executeflow_txt != null) {
            executeflow_txt.setscreen3controller(this);
            executeflow_txt.getAirPollution(latitude, longitude, null);
        }

    }

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

    public void setAirPollutionData(AirPollutionAPIData DataObject) {
        int aqi = DataObject.getAqi();
        double co = DataObject.getCo();
        double nh3 = DataObject.getNh3();
        double no = DataObject.getNo();
        double no2 = DataObject.getNo2();
        double o3 = DataObject.getO3();
        double pm10 = DataObject.getPm10();
        double pm2_5 = DataObject.getPm25();
        double so2 = DataObject.getSo2();
        tfAirQualityIndex.setText(Integer.toString(aqi));
        tfCO.setText(String.format("%.2f", co));
        tfNH3.setText(String.format("%.2f", nh3));
        tfNO.setText(String.format("%.2f", no));
        tfNO2.setText(String.format("%.2f", no2));
        tfO3.setText(String.format("%.2f", o3));
        tfPM10.setText(String.format("%.2f", pm10));
        tfPM2.setText(String.format("%.2f", pm2_5));
        tfSO2.setText(String.format("%.2f", so2));
    }
}
