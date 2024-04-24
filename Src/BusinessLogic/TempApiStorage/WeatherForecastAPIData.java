package Src.BusinessLogic.TempApiStorage;

import java.util.Arrays;

public class WeatherForecastAPIData extends APIdataStorage {
  private double[][] data = new double[5][5];
  private String[] iconUrls = new String[5];
  private String[] weatherCondition = new String[5];

  public double[][] getData() {
    return data;
  }

  public void setData(double[][] data) {
    for (int i = 0; i < data.length; i++) {
      this.data[i] = Arrays.copyOf(data[i], data[i].length);
    }
  }

  public String[] getIconUrls() {
    return iconUrls;
  }

  public void setIconUrls(String[] iconUrls) {
    this.iconUrls = Arrays.copyOf(iconUrls, iconUrls.length);
  }

  public String[] getWeatherCondition() {
    return weatherCondition;
  }

  public void setWeatherCondition(String[] weatherCondition) {
    this.weatherCondition = Arrays.copyOf(weatherCondition, weatherCondition.length);
  }
}
