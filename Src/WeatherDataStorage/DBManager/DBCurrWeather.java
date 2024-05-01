package Src.WeatherDataStorage.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Src.BusinessLogic.TempApiStorage.CurrentWeatherAPIData;
import Src.WeatherDataStorage.jdbcDriver.MySQLConnection;
import Src.WeatherDataStorage.DBManager.interfaces.CurrWeatherCache;

public class DBCurrWeather implements CurrWeatherCache {
    private MySQLConnection Connection = new MySQLConnection();
    private CurrentWeatherAPIData obj = new CurrentWeatherAPIData();

    private int locationIdCounter = 0;

    // Implementing interface methods
    @Override
    public Boolean checkCurrentWeatherData(double latitude, double longitude) {
        return isDataPresentByLatLon(latitude, longitude);
    }

    @Override
    public Boolean checkCurrentWeatherData(String cityName) {
        return isDataPresentByCityName(cityName);
    }

    @Override
    public void fetchCurrentWeatherData(CurrentWeatherAPIData currentWeather, double latitude, double longitude) {
        currentWeather = displayDataFromDatabaseByLatLon(latitude, longitude);
    }

    @Override
    public void fetchCurrentWeatherData(CurrentWeatherAPIData currentWeather, String cityName) {
        currentWeather = displayDataFromDatabaseByCityName(cityName);
    }
    public void insertWeatherData(CurrentWeatherAPIData jsonObject) {
        try (Connection connection = MySQLConnection.getConnection()) {
            // Query to get the last location ID
            String lastLocationIdQuery = "SELECT MAX(loc_id) FROM Current_Weather_Data";
            PreparedStatement lastLocationIdStatement = connection.prepareStatement(lastLocationIdQuery);
            ResultSet lastLocationIdResult = lastLocationIdStatement.executeQuery();
            int lastLocationId = 0;
            if (lastLocationIdResult.next()) {
                lastLocationId = lastLocationIdResult.getInt(1);
            }
            int locationIdCounter = lastLocationId + 1; // Assigning the new location ID

            float lat = jsonObject.getLatitude();
            float lon = jsonObject.getLongitude();
            String cityName = jsonObject.getCityName();// Changed variable name to cityName
            String weatherMain = jsonObject.getWeatherMain();
            String weatherDescription = jsonObject.getWeatherDescription();
            String weatherIcon = jsonObject.getWeatherIcon(); // Icon of current weather
            float temp = jsonObject.getTemperature();
            float feelsLike = jsonObject.getFeelsLike();
            float tempMin = jsonObject.getTempMin();
            float tempMax = jsonObject.getTempMax();
            int pressure = jsonObject.getPressure();
            int humidity = jsonObject.getHumidity();
            int visibility = jsonObject.getVisibility();
            float windSpeed = jsonObject.getWindSpeed();
            double rainVolume = jsonObject.getRain(); // Assume rain volume is in "1h" key
            int cloudsAll = jsonObject.getCloudsAll();
            int dt = jsonObject.getDt(); // Time of data calculation
            String country = jsonObject.getCountry();
            int sunrise = jsonObject.getSunrise(); // Sunrise Time
            int sunset = jsonObject.getSunset();
            int timezone = jsonObject.getTimezone();
            // Store data in the database
            String insertSql = "INSERT INTO Current_Weather_Data (loc_id, city_name, latitude, longitude, weather_main, weather_description, weather_icon, temperature, feels_like, temp_min, temp_max, pressure, humidity, visibility, wind_speed, rain, clouds_all, dt, country, sunrise, sunset, timezone) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setInt(1, locationIdCounter);
                preparedStatement.setString(2, cityName);
                preparedStatement.setDouble(3, lat);
                preparedStatement.setDouble(4, lon);
                preparedStatement.setString(5, weatherMain);
                preparedStatement.setString(6, weatherDescription);
                preparedStatement.setString(7, weatherIcon);
                preparedStatement.setDouble(8, temp);
                preparedStatement.setDouble(9, feelsLike);
                preparedStatement.setDouble(10, tempMin);
                preparedStatement.setDouble(11, tempMax);
                preparedStatement.setInt(12, pressure);
                preparedStatement.setInt(13, humidity);
                preparedStatement.setInt(14, visibility);
                preparedStatement.setDouble(15, windSpeed);
                preparedStatement.setDouble(16, rainVolume);
                preparedStatement.setInt(17, cloudsAll);
                preparedStatement.setInt(18, dt);
                preparedStatement.setString(19, country);
                preparedStatement.setInt(20, sunrise);
                preparedStatement.setInt(21, sunset);
                preparedStatement.setInt(22, timezone);

                // Execute the SQL statement
                preparedStatement.executeUpdate();

                System.out.println("Data inserted successfully!"); // Only a check to see if data inserted or not
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDataPresentByCityName(String cityName) {
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM Current_Weather_Data WHERE city_name = ?";
        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setString(1, cityName);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                present = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return present;
    }

    public boolean isDataPresentByLatLon(double latitude, double longitude) {
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM Current_Weather_Data WHERE latitude = ? AND longitude = ?";
        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setDouble(1, latitude);
            selectStatement.setDouble(2, longitude);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                present = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return present;
    }

    public CurrentWeatherAPIData displayDataFromDatabaseByLatLon(double latitude, double longitude) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM Current_Weather_Data WHERE latitude = ? AND longitude = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.obj = displayData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public CurrentWeatherAPIData displayDataFromDatabaseByCityName(String cityName) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM Current_Weather_Data WHERE city_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.obj = displayData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    // Helper method to display fetched data from ResultSet
    private CurrentWeatherAPIData displayData(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            // Extract data from ResultSet
            int loc_id = resultSet.getInt("loc_id");
            String city_name = resultSet.getString("city_name");
            float lat = resultSet.getFloat("latitude");
            float lon = resultSet.getFloat("longitude");
            String weather_main = resultSet.getString("weather_main");
            String weather_description = resultSet.getString("weather_description");
            String weather_icon = resultSet.getString("weather_icon");
            float temperature = resultSet.getFloat("temperature");
            float feels_like = resultSet.getFloat("feels_like");
            float temp_min = resultSet.getFloat("temp_min");
            float temp_max = resultSet.getFloat("temp_max");
            int pressure = resultSet.getInt("pressure");
            int humidity = resultSet.getInt("humidity");
            int visibility = resultSet.getInt("visibility");
            float wind_speed = resultSet.getFloat("wind_speed");
            float rain = resultSet.getFloat("rain");
            int clouds_all = resultSet.getInt("clouds_all");
            int dt = resultSet.getInt("dt");
            String country = resultSet.getString("country");
            int sunrise = resultSet.getInt("sunrise");
            int sunset = resultSet.getInt("sunset");
            int timezone = resultSet.getInt("timezone");
            String baseIconUrl = "https://openweathermap.org/img/wn/"; // url for the icons
            String iconUrl = resultSet.getString("weather_icon");

            // make temporary CurrWeather APi obj to store data for ui display
            obj.setLocId(1);
            obj.setCityName(city_name);
            obj.setLatitude(lat);
            obj.setLongitude(lon);
            obj.setWeatherDescription(weather_description);
            obj.setWeatherIcon(iconUrl);
            obj.setWeatherMain(weather_main);
            obj.setTemperature(temperature);
            obj.setFeelsLike(feels_like);
            obj.setTempMax(temp_max);
            obj.setTempMin(temp_min);
            obj.setPressure(pressure);
            obj.setHumidity(humidity);
            obj.setVisibility(visibility);
            obj.setWindSpeed(wind_speed);
            obj.setRain(rain);
            obj.setCloudsAll(clouds_all);
            obj.setDt(dt);
            obj.setCountry(country);
            obj.setSunrise(sunrise);
            obj.setSunset(sunset);
            obj.setTimezone(timezone);
        }
        return obj;
    }

    // Delete data in table after 6 hours
    public void deleteOldData() {
        try (Connection connection = MySQLConnection.getConnection()) {
            Instant sixHoursAgo = Instant.now().minus(Duration.ofHours(6));
            long sixHoursAgoEpoch = sixHoursAgo.getEpochSecond();
            String deleteSql = "DELETE FROM Current_Weather_Data WHERE dt < ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setLong(1, sixHoursAgoEpoch);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBCurrWeather fetcher = new DBCurrWeather();

        // Schedule deletion of old data every 6 hours
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(fetcher::deleteOldData, 0, 6, TimeUnit.HOURS);
        System.err.println("Insertion Sucessful");
    }

}
