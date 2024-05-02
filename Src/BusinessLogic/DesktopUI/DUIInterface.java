package Src.BusinessLogic.DesktopUI;

interface DUIInterface {
    void SearchByCity(String cityName);

    void SearchByCoord(double lat, double lon);

    void Flow(String city);

    void Flow(double lat, double lon);
}
