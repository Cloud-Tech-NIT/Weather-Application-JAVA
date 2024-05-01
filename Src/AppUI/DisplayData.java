package Src.AppUI;

import Src.BusinessLogic.TempApiStorage.AirPollutionAPIData;
import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;

public interface DisplayData {
        // interface btw APIs and Terminal to display data--api callback to the func and
        // the terminal implements the func
        void RetriveAirPollutionData(AirPollutionAPIData AirPoll);
        void RetriveCurrentWeatherData(CurrentWeatherAPIData currentWeatherData);
        void RetriveWeatherForecastData(WeatherForecastAPIData weatherForecastData);


}