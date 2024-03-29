
package Src.WeatherDataStorage;

/**
 * DataHandlingInterface
 */
public interface CacheManager {

  public Boolean checkData(String filename,double latitude, double longitude);

  public Boolean checkData(String filename,String cityName);

  public String FetchData(String filename,double latitude, double longitude);

  public String FetchData(String filename,String cityName);

}