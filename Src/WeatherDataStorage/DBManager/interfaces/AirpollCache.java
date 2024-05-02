package Src.WeatherDataStorage.DBManager.interfaces;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.WeatherDataStorage.CacheManager;

public interface AirpollCache
{
    public Boolean checkAirPollutionData(double latitude, double longitude);

    public Boolean checkAirPollutionData(String cityName);

    public void fetchAirPollutionData(AirPollutionAPIData AirPoll, double latitude, double longitude);

    public void fetchAirPollutionData(AirPollutionAPIData AirPoll, String cityName);
}
