CREATE TABLE weather_forecast (
    loc_id INT PRIMARY KEY,
    city_name VARCHAR(255),
    latitude FLOAT,
    longitude FLOAT,
    date DATE,
    temp FLOAT,
    temp_min FLOAT,
    temp_max FLOAT,
    pressure FLOAT,
    humidity FLOAT,
    city_coordinates VARCHAR(255)
);