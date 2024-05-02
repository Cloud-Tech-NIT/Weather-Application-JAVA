package Src.OpenWeatherAPI;

import java.net.URL;

public interface InterfaceAPI {
  String APIkey = "cc7d0c84d9aca07ad0bc1494b2af27a0";
  String units = "metric";

  URL APIcall(double latitude, double longitude); // to get data from longitude and latitude

  URL APIcall(String cityName);

}