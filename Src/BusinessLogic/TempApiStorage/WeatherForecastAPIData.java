package Src.BusinessLogic.TempApiStorage;

import java.util.Arrays;

public class WeatherForecastAPIData extends APIdataStorage {
  private double[][] data = new double[5][5];
  private String[] iconUrls = new String[5];
  private String[] weatherCondition = new String[5];

  public double[][] getData() {
    return data;
  }

  public void printData() {
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        System.out.println("data[" + i + "][" + j + "] = " + data[i][j]);
      }
    }

    // Displaying elements of the iconUrls array
    System.out.println("iconUrls:");
    for (int i = 0; i < iconUrls.length; i++) {
      System.out.println("iconUrls[" + i + "] = " + iconUrls[i]);
    }

    // Displaying elements of the weatherCondition array
    System.out.println("weatherCondition:");
    for (int i = 0; i < weatherCondition.length; i++) {
      System.out.println("weatherCondition[" + i + "] = " + weatherCondition[i]);
    }

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
