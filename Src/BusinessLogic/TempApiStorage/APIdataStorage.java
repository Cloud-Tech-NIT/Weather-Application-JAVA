package Src.BusinessLogic.TempApiStorage;

public class APIdataStorage {
  protected int locId;
  protected String cityName;
  protected float latitude;
  protected float longitude;
  protected int dt;

  public int getLocId() {
    return locId;
  }

  public void setLocId(int locId) {
    this.locId = locId;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }

  public int getDt() {
    return dt;
  }

  public void setDt(int dt) {
    this.dt = dt;
  }
}
