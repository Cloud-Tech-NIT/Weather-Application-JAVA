package Src.OpenWeatherAPI;

public interface notificationInterface {
  public static final int POOR_WEATHER_THRESHOLD = 100;
  public static final int POOR_AIR_QUALITY_THRESHOLD = 1;

  public void generateNotification(int condition);
}
