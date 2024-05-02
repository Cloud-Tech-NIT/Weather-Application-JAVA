package Src.AppUI;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import Src.BusinessLogic.DUIFiller;
import Src.BusinessLogic.DesktopUI.DUI_DB;
import Src.BusinessLogic.DesktopUI.DUI_Txt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class mainscreen2controller implements NotificationInterface {

    // private final DUIFiller executeflow;
    public String database_used = "Txt";

    private DUI_DB executeflow_sql;
    private DUI_Txt executeflow_txt;

    public void setdb(String db) {
        this.database_used = "Txt";
    }

    public mainscreen2controller(String db) {
        this.database_used = "Txt";

        if ("SQL".equals(database_used)) {

            executeflow_sql = new DUI_DB(this);

        } else if ("Txt".equals(database_used)) {
            executeflow_txt = new DUI_Txt(this);

        }

    }

    @FXML
    void initialize() {

        if ("SQL".equals(database_used)) {

            executeflow_sql = new DUI_DB(this);
            executeflow_sql.Flow("Lahore");
        } else if ("Txt".equals(database_used)) {
            executeflow_txt = new DUI_Txt(this);
            executeflow_txt.Flow("Lahore");
        }
        // Set current day's day and date
        setDayAndDate();
    }

    // Method to set the current day's day and date
    private void setDayAndDate() {
        LocalDate currentDate = LocalDate.now();
        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        String date = currentDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        tfDayAndDate.setText(dayOfWeek + ", " + date);
    }

    public mainscreen2controller() {
        // Default constructor
    }

    private Stage mainWindow;

    public void setMainWindow(Stage mainwindow) {

        this.mainWindow = mainwindow;
    }

    public void initialize(String lat, String lon) {
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        if ("SQL".equals(database_used)) {
            executeflow_sql.Flow(latitude, longitude);
        } else if ("Txt".equals(database_used)) {
            executeflow_txt.Flow(latitude, longitude);
        }
    }

    public void initialize(String cityName) {
        if ("SQL".equals(database_used)) {
            executeflow_sql.Flow(cityName);
        } else if ("Txt".equals(database_used)) {
            executeflow_txt.Flow(cityName);
        }
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
    private Label tfDay1MaxTemp;

    @FXML
    private Label tfDay1MinTemp;

    @FXML
    private Label tfDay1WeatherConditon;

    @FXML
    private Label tfDay2;

    @FXML
    private ImageView tfDay2Icon;

    @FXML
    private Label tfDay2MaxTemp;

    @FXML
    private Label tfDay2MinTemp;

    @FXML
    private Label tfDay2WeatherConditon;

    @FXML
    private Label tfDay3;

    @FXML
    private ImageView tfDay3Icon;

    @FXML
    private Label tfDay3MaxTemp;

    @FXML
    private Label tfDay3MinTemp;

    @FXML
    private Label tfDay3WeatherConditon;

    @FXML
    private Label tfDay4;

    @FXML
    private ImageView tfDay4Icon;

    @FXML
    private Label tfDay4MaxTemp;

    @FXML
    private Label tfDay4MinTemp;

    @FXML
    private Label tfDay4WeatherConditon;

    @FXML
    private Label tfDay5;

    @FXML
    private ImageView tfDay5Icon;

    @FXML
    private Label tfDay5MaxTemp;

    @FXML
    private Label tfDay5MinTemp;
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
            controller.initialize(latitude, longitude, database_used, executeflow_sql, executeflow_txt); // Pass city
                                                                                                         // name to
                                                                                                         // Screen2Controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Air pollution");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    void SearchWeatherButton(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Src/AppUI/weatherui2.fxml"));
            Parent root = loader.load();
            Ui2controller controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Search Weather");
            stage.setScene(new Scene(root));
            stage.show();
            // Close the current stage (optional)
            Node sourceNode = (Node) event.getSource();
            Stage currentStage = (Stage) sourceNode.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

    }
    
    @Override
    public void showNotification(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void updateUI(mainscreen2controller controller, CurrentWeatherAPIData jsonObject) {
        // Convert temperature units from Kelvin to Celsius
        double lat = jsonObject.getLatitude();
        double lon = jsonObject.getLongitude();
        String cityName = jsonObject.getCityName();// Changed variable name to cityName
        String weatherCondition = jsonObject.getWeatherMain();
        String weatherIconURL = jsonObject.getWeatherIcon(); // Icon of current weather
        double currentTemp = jsonObject.getTemperature();
        double feelsLike = jsonObject.getFeelsLike();
        double tempMin = jsonObject.getTempMin();
        double tempMax = jsonObject.getTempMax();
        int pressure = jsonObject.getPressure();
        int humidity = jsonObject.getHumidity();
        double windSpeed = jsonObject.getWindSpeed();
        String countryName = jsonObject.getCountry();
        int sunrise = jsonObject.getSunrise(); // Sunrise Time
        int sunset = jsonObject.getSunset();
        int timezone = jsonObject.getTimezone();
        int visibility = jsonObject.getVisibility();

        // Format the temperature to have one digit after the decimal point
        String formattedTemperature = String.format("%.1f", currentTemp);
        String formattedFeelsLike = String.format("%.1f", feelsLike);
        String formattedTempMin = String.format("%.1f", tempMin);
        String formattedTempMax = String.format("%.1f", tempMax);
        String formattedWindSpeed = String.format("%.1f", windSpeed);

        // Set data to respective labels
        controller.tfCityAndCountryName.setText(cityName + ", " + countryName);
        controller.tfCurrentTemp.setText(formattedTemperature + " °C");
        controller.tfCurrentWeatherConditon.setText(weatherCondition);
        try {
            Image image = new Image(weatherIconURL);
            controller.tfCurrentWeatherIcon.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading image from URL: " + weatherIconURL);
            e.printStackTrace();
        }
        controller.tfLatitude.setText(Double.toString(lat));
        controller.tfLongitude.setText(Double.toString(lon));
        controller.tfFeelsLike.setText(formattedFeelsLike + " °C");
        controller.tfHumidity.setText(Integer.toString(humidity) + " %");
        controller.tfMinimumTemp.setText(formattedTempMin + " °C");
        controller.tfMaximumTemp.setText(formattedTempMax + " °C");
        controller.tfSunrise.setText(formatTime(sunrise, timezone));
        controller.tfSunset.setText(formatTime(sunset, timezone));
        controller.tfPressure.setText(Integer.toString(pressure) + " hPa");
        controller.tfWindspeed.setText(formattedWindSpeed + " m/s");

        if (visibility < visibilityThreshold) {
            String message = "Low Visibility Alert: Visibility is " + visibility + " meters.";
            showNotification("Low Visibility Alert ", message, AlertType.WARNING);
        }

        
    }

    


    // Method to format time in hh:mm format
    private String formatTime(int timeInSeconds, int timezone) {
        // Convert sunrise and sunset from Unix timestamp to LocalDateTime
        LocalDateTime sunriseTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timeInSeconds),
                ZoneOffset.ofTotalSeconds(timezone));

        // Define the date time formatter for AM/PM format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");

        // Format sunrise and sunset times to AM/PM format
        String Sunrise = sunriseTime.format(formatter);
        return Sunrise;
    }

    public void updateForecast(WeatherForecastAPIData DataObj) {
        double[][] data = DataObj.getData();
        String[] iconUrls = DataObj.getIconUrls();
        String[] weatherConditions = DataObj.getWeatherCondition();
        // Update UI with forecast data for each day
        for (int i = 0; i < 5; i++) {
            // double temperature = data[i][0];
            String weatherCondition = weatherConditions[i];
            String iconUrl = iconUrls[i];
            double minTemperature = data[i][1];
            double maxTemperature = data[i][2];

            // Update UI for the ith day
            updateForecastForDay(i, minTemperature, maxTemperature, weatherCondition, iconUrl);
        }
    }

    // Method to update forecast data for a specific day in UI
    public void updateForecastForDay(int dayIndex, double minTemperature, double maxTemperature,
            String weatherCondition,
            String iconUrl) {
        // Update UI labels for the respective day
        switch (dayIndex) {
            case 0:
                tfDay1.setText(getDayName(dayIndex)); // Update day label
                tfDay1MinTemp.setText(formatTemperature(minTemperature) + " °C"); // Update minimum temperature label
                tfDay1MaxTemp.setText(formatTemperature(maxTemperature) + " °C"); // Update maximum temperature label
                tfDay1WeatherConditon.setText(weatherCondition); // Update weather condition label
                tfDay1Icon.setImage(new Image(iconUrl)); // Update weather icon
                break;
            case 1:
                tfDay2.setText(getDayName(dayIndex));
                tfDay2MinTemp.setText(formatTemperature(minTemperature) + " °C");
                tfDay2MaxTemp.setText(formatTemperature(maxTemperature) + " °C");
                tfDay2WeatherConditon.setText(weatherCondition);
                tfDay2Icon.setImage(new Image(iconUrl));
                break;
            case 2:
                tfDay3.setText(getDayName(dayIndex));
                tfDay3MinTemp.setText(formatTemperature(minTemperature) + " °C");
                tfDay3MaxTemp.setText(formatTemperature(maxTemperature) + " °C");
                tfDay3WeatherConditon.setText(weatherCondition);
                tfDay3Icon.setImage(new Image(iconUrl));
                break;
            case 3:
                tfDay4.setText(getDayName(dayIndex));
                tfDay4MinTemp.setText(formatTemperature(minTemperature) + " °C");
                tfDay4MaxTemp.setText(formatTemperature(maxTemperature) + " °C");
                tfDay4WeatherConditon.setText(weatherCondition);
                tfDay4Icon.setImage(new Image(iconUrl));
                break;
            case 4:
                tfDay5.setText(getDayName(dayIndex));
                tfDay5MinTemp.setText(formatTemperature(minTemperature) + " °C");
                tfDay5MaxTemp.setText(formatTemperature(maxTemperature) + " °C");
                tfDay5WeatherConditon.setText(weatherCondition);
                tfDay5Icon.setImage(new Image(iconUrl));
                break;
            default:
                break;
        }
    }

    // Method to get day name based on day index (0 for today, 1 for tomorrow, etc.)
    private String getDayName(int dayIndex) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Add the day index to the current date to get the desired date
        LocalDate desiredDate = currentDate.plusDays(dayIndex + 1);

        // Get the localized name of the day
        Locale locale = Locale.getDefault(); // Use default locale
        String dayName = desiredDate.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);

        return dayName;
    }

    // Method to format temperature
    private String formatTemperature(double temperature) {
        double temperatureInCelsius = temperature;

        // Format the temperature to have one digit after the decimal point
        String formattedTemperature = String.format("%.1f", temperatureInCelsius);
        return formattedTemperature;
    }
}
