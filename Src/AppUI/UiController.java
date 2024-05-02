package Src.AppUI;

import javafx.scene.Node;
import java.io.IOException;

//import com.google.gson.Gson;
// import com.google.gson.JsonObject;
// import com.google.gson.JsonParser;

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

    @SuppressWarnings("unused")
    private Stage mainWindow;

    public void setMainWindow(Stage mainwindow) {
        this.mainWindow = mainwindow;
    }

    @FXML
    void oncitysearch(ActionEvent event) {
        String cityName = tfcityname.getText();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Src/AppUI/mainscreen.fxml"));
            Parent root = loader.load();
            mainscreenController controller = loader.getController();
            controller.initialize(cityName); // Pass city name to Screen2Controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Node sourceNode = (Node) event.getSource();
            Stage currentStage = (Stage) sourceNode.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @FXML

    void onlatsearch(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Src/AppUI/mainscreen.fxml"));
            Parent root = loader.load();
            mainscreenController controller = loader.getController();
            controller.initialize(tflatitude.getText(), tflongitude.getText()); // Pass city name to
                                                                                // Screen2Controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Node sourceNode = (Node) event.getSource();
            Stage currentStage = (Stage) sourceNode.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    public void initialize() {
        // Add initial items to the ListView
        locationList.getItems().addAll("Lahore");
    }

}
