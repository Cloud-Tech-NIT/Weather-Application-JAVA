package Src.WeatherDataStorage;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;

public interface StoreTxt {
     void storeAirPollutionData(AirPollutionAPIData airPollutionData);
     void storeCurrentWeatherData(CurrentWeatherAPIData currentWeatherData);
     void storeWeatherForecastData(WeatherForecastAPIData data);
     void deleteOldData();
}
