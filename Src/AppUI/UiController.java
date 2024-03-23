package Src.AppUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UiController {

    @FXML
    private ListView<String> locationList;

    @FXML
    private TextField tfcityname;

    @FXML
    private TextField tflatitude;

    @FXML
    private TextField tflongitude;

    private Stage mainWindow;

    public void setMainWindow(Stage mainwindow) {
        this.mainWindow = mainwindow;
    }

    @FXML
    void oncitysearch(ActionEvent event) {
        String cityName = tfcityname.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Src/AppUI/screen2.fxml"));
            Parent root = loader.load();
            Screen2Controller controller = loader.getController();
            controller.initialize(cityName); // Pass city name to Screen2Controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML
    void onlatsearch(ActionEvent event) {

    }

    public void initialize() {
        // Add initial items to the ListView
        locationList.getItems().addAll("Lahore");
    }

}
