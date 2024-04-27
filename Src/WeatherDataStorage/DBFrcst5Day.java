package Src.WeatherDataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Src.BusinessLogic.TempApiStorage.WeatherForecastAPIData;
import Src.WeatherDataStorage.MySQLConnection;

public class DBFrcst5Day {
    private MySQLConnection Connection = new MySQLConnection();
    WeatherForecastAPIData apiData = new WeatherForecastAPIData();

    public void insertWeatherData(WeatherForecastAPIData jsonObject) {
        try (Connection connection = MySQLConnection.getConnection()) {
            int dayIndex = 0;
            float lat = jsonObject.getLatitude();
            float lon = jsonObject.getLongitude();
            String cityName = jsonObject.getCityName();
            double[][] data = jsonObject.getData();
            String[] iconUrls = jsonObject.getIconUrls();
            String[] weatherConditions = jsonObject.getWeatherCondition();

            String insertSql = "INSERT INTO weather_forecast (Day, city_name, latitude, longitude, temp, temp_min, temp_max, pressure, humidity, weather_condition, icon_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connect = MySQLConnection.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                for (int i = 0; i < 5; i++) {
                    preparedStatement.setInt(1, dayIndex);
                    preparedStatement.setString(2, cityName);
                    preparedStatement.setDouble(3, lat);
                    preparedStatement.setDouble(4, lon);
                    preparedStatement.setDouble(5, data[i][0]); // Assuming data[i][0] is the temperature
                    preparedStatement.setDouble(6, data[i][1]); // Assuming data[i][1] is the minimum temperature
                    preparedStatement.setDouble(7, data[i][2]); // Assuming data[i][2] is the maximum temperature
                    preparedStatement.setDouble(8, data[i][3]); // Assuming data[i][3] is the pressure
                    preparedStatement.setDouble(9, data[i][4]); // Assuming data[i][4] is the humidity
                    preparedStatement.setString(10, weatherConditions[i]);
                    preparedStatement.setString(11, iconUrls[i]);
                    preparedStatement.executeUpdate();
                    dayIndex++;
                }

                System.out.println("Data inserted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDataPresentByCityName(String cityName) {
        DBFrcst5Day fetcher = new DBFrcst5Day();
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM weather_forecast WHERE city_name = ?";
        try (Connection connect = MySQLConnection.getConnection()) {
            PreparedStatement selectStatement = connect.prepareStatement(selectSql);
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
        DBFrcst5Day fetcher = new DBFrcst5Day();
        boolean present = false;
        String selectSql = "SELECT COUNT(*) FROM weather_Forecast WHERE latitude = ? AND longitude = ?";
        try (Connection connect = MySQLConnection.getConnection()) {
            PreparedStatement selectStatement = connect.prepareStatement(selectSql);
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

    public WeatherForecastAPIData displayDataFromDatabaseByCityName(String cityName) {
        try (Connection connect = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM weather_Forecast WHERE city_name = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.apiData = displayDataAsArray(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apiData;
    }

    public WeatherForecastAPIData displayDataFromDatabaseByLatLon(double latitude, double longitude) {
        try (Connection connect = MySQLConnection.getConnection()) {
            String query = "SELECT * FROM weather_Forecast WHERE latitude = ? AND longitude = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.apiData = displayDataAsArray(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apiData;
    }

    // Function to display fetched data as array
    public WeatherForecastAPIData displayDataAsArray(ResultSet resultSet) throws SQLException {
        double[][] data = new double[5][5];
        String[] iconUrls = new String[5];
        String[] weatherConditions = new String[5];

        try (Connection connect = MySQLConnection.getConnection()) {
            int dayIndex = 0;

            while (resultSet.next() && dayIndex < 5) {

                data[dayIndex][0] = resultSet.getDouble("temp");
                data[dayIndex][1] = resultSet.getDouble("temp_min");
                data[dayIndex][2] = resultSet.getDouble("temp_max");
                data[dayIndex][3] = resultSet.getDouble("pressure");
                data[dayIndex][4] = resultSet.getDouble("humidity");
                iconUrls[dayIndex] = resultSet.getString("icon_url");
                weatherConditions[dayIndex] = resultSet.getString("weather_condition");
                dayIndex++;

            }

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    System.out.println("data[" + i + "][" + j + "] = " + data[i][j]);
                }
            }

            // Displaying elements of the iconUrls array
            System.out.println("iconUrls:");
            for (int i = 0; i < iconUrls.length; i++) {
                System.out.println("iconUrls[" + i + "] = " + iconUrls[i]);
            }

            // Displaying elements of the weatherCondition array
            System.out.println("weatherCondition:");
            for (int i = 0; i < weatherConditions.length; i++) {
                System.out.println("weatherCondition[" + i + "] = " + weatherConditions[i]);
            }

            apiData.setData(data);
            apiData.setIconUrls(iconUrls);
            apiData.setWeatherCondition(weatherConditions);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apiData;
    }

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
        DBFrcst5Day dbFrcst = new DBFrcst5Day();

        // Example usage: Insert weather data
        WeatherForecastAPIData weatherData = new WeatherForecastAPIData();
        // Set weather data in the WeatherForecastAPIData object
        dbFrcst.insertWeatherData(weatherData);

    }

}
