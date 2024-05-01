package Src.BusinessLogic.TempApiStorage;

public class CurrentWeatherAPIData extends APIdataStorage {
  private String weatherMain;
  private String weatherDescription;
  private String weatherIcon;
  private float temperature;
  private float feelsLike;
  private float tempMin;
  private float tempMax;
  private int pressure;
  private int humidity;
  private int visibility;
  private float windSpeed;
  private float rain;
  private int cloudsAll;
  private String country;
  private int sunrise;
  private int sunset;
  private int timezone;
  private int weatherID;
  private int windDeg;

  public String getWeatherMain() {
    return weatherMain;
  }

  public void setWeatherMain(String weatherMain) {
    this.weatherMain = weatherMain;
  }

  public String getWeatherDescription() {
    return weatherDescription;
  }

  public void setWeatherDescription(String weatherDescription) {
    this.weatherDescription = weatherDescription;
  }

  public String getWeatherIcon() {
    return weatherIcon;
  }

  public void setWeatherIcon(String weatherIcon) {
    this.weatherIcon = weatherIcon;
  }

  public float getTemperature() {
    return temperature;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public float getFeelsLike() {
    return feelsLike;
  }

  public void setFeelsLike(float feelsLike) {
    this.feelsLike = feelsLike;
  }

  public float getTempMin() {
    return tempMin;
  }

  public void setTempMin(float tempMin) {
    this.tempMin = tempMin;
  }

  public float getTempMax() {
    return tempMax;
  }

  public void setTempMax(float tempMax) {
    this.tempMax = tempMax;
  }

  public int getPressure() {
    return pressure;
  }

  public void setPressure(int pressure) {
    this.pressure = pressure;
  }

  public int getHumidity() {
    return humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }

  public int getVisibility() {
    return visibility;
  }

  public void setVisibility(int visibility) {
    this.visibility = visibility;
  }

  public float getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(float windSpeed) {
    this.windSpeed = windSpeed;
  }

  public float getRain() {
    return rain;
  }

  public void setRain(float rain) {
    this.rain = rain;
  }

  public int getCloudsAll() {
    return cloudsAll;
  }

  public void setCloudsAll(int cloudsAll) {
    this.cloudsAll = cloudsAll;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public int getSunrise() {
    return sunrise;
  }

  public void setSunrise(int sunrise) {
    this.sunrise = sunrise;
  }

  public int getSunset() {
    return sunset;
  }

  public void setSunset(int sunset) {
    this.sunset = sunset;
  }

  public int getTimezone() {
    return timezone;
  }

  public void setTimezone(int timezone) {
    this.timezone = timezone;
  }

  public int getWeatherID() {
    return weatherID;
  }

  public void setWeatherID(int weatherID) {
    this.weatherID = weatherID;
  }

  public int getWindDeg() {
    return windDeg;
  }

  public void setWindDeg(int windDeg) {
    this.windDeg = windDeg;
  }
}
