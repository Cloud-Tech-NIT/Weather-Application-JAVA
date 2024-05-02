package Src.AppUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private mainscreenController controller;
    String db;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Src/AppUI/mainscreen.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setdb(db);
        controller.setMainWindow(primaryStage);
        primaryStage.setTitle("Weather App");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    public void startWithController(String db, mainscreenController controller) {
        this.controller = controller;
        this.db = db;
        launch();
    }

    public static void main(String[] args) {
        launch(args);
    }
}