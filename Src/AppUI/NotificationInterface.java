package Src.AppUI;

import javafx.scene.control.Alert.AlertType;

public interface NotificationInterface {
    int visibilityThreshold = 8000;
    int aqiThreshold = 4;

    public abstract void showNotification(String title, String message, AlertType alertType);
}
