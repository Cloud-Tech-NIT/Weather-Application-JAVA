
package Src.WeatherDataStorage;

/**
 * DataHandlingInterface
 */
public interface CacheManager {

  public Boolean checkData(double latitude, double longitude);

  public Boolean checkData(String cityName);

  public String FetchData();

}