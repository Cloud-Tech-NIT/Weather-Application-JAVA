package Src.AppUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App2 extends Application {
    private mainscreen2controller controller;
    String db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("im in start");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Src/AppUI/mainscreen2.fxml"));
        Parent root = loader.load();
        System.out.println("im in start");
        controller = loader.getController();
        controller.setdb("Txt");
        controller.setMainWindow(primaryStage);
        primaryStage.setTitle("Weather App");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    public void startWithController(String db, mainscreen2controller controller) {
        this.controller = controller;
        this.db = db;
        launch();
    }

    public static void main(String[] args) {

        launch(args);
    }
}