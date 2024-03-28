package Src.WeatherDataStorage;

public class DataHandlingTxT implements CacheManager {
  Boolean found = false;

  @Override
  public Boolean checkData(double latitude, double longitude) {
    return found;
  }

  @Override
  public Boolean checkData(String cityName) {
    return found;
  }

  @Override
  public String FetchData() {

    String Response = "";
    return Response;
  }
}
