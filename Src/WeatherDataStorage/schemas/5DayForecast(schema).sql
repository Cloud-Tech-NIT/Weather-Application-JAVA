CREATE TABLE weather_forecast (
    Day INT PRIMARY KEY,
    city_name VARCHAR(255) PRIMARY KEY,
    latitude FLOAT,
    longitude FLOAT,
    temp FLOAT,
    temp_min FLOAT,
    temp_max FLOAT,
    pressure FLOAT,
    humidity FLOAT,
    weather_condition VARCHAR(255),
    icon_url VARCHAR(255)
);
