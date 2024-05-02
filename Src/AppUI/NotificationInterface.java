package Src.AppUI;

import javafx.scene.control.Alert.AlertType;

public interface NotificationInterface {
    int visibilityThreshold = 8000;
    int aqiThreshold = 1;

    public abstract void showNotification(String title, String message, AlertType alertType);
}
