CREATE TABLE Air_Pollution_Data (
     loc_id INT PRIMARY KEY,
    city_name VARCHAR(255),
    latitude FLOAT,
    longitude FLOAT,
    dt BIGINT,
    aqi INT,
    co FLOAT,
    [no] FLOAT,
    no2 FLOAT,
    o3 FLOAT,
    so2 FLOAT,
    pm2_5 FLOAT,
    pm10 FLOAT,
    nh3 FLOAT
);
