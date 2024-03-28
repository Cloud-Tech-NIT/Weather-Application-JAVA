CREATE TABLE WeatherForecast (
    id INT PRIMARY KEY,
    date DATE,
    temp FLOAT,
    temp_min FLOAT,
    temp_max FLOAT,
    pressure FLOAT,
    humidity FLOAT,
    city_coordinates VARCHAR(255)
);