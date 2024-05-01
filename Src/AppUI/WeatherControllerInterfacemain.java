package Src.AppUI;

import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;

public interface WeatherControllerInterfacemain {
    void updateUI(mainscreenController controller, CurrentWeatherAPIData jsonObject);

    void updateForecast(WeatherForecastAPIData dataObj);
}
