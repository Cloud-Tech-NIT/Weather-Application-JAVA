-- Create a table to store location information
CREATE TABLE Locations (
    LocationID INT PRIMARY KEY IDENTITY(1,1),
    City VARCHAR(255),
    Country VARCHAR(255),
    Latitude FLOAT,
    Longitude FLOAT
);

-- Create a table to store current weather data
CREATE TABLE CurrentWeather (
    WeatherID INT PRIMARY KEY IDENTITY(1,1),
    LocationID INT,
    Timestamp DATETIME,
    Temperature FLOAT,
    FeelsLike FLOAT,
    MinTemp FLOAT,
    MaxTemp FLOAT,
    Humidity INT,
    Pressure INT,
    WindSpeed FLOAT,
    WindDeg INT,
    Sunrise DATETIME,
    Sunset DATETIME,
    WeatherDescription VARCHAR(255),
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

-- Create a table to store 5-day forecast data
CREATE TABLE ForecastWeather (
    ForecastID INT PRIMARY KEY IDENTITY(1,1),
    LocationID INT,
    Timestamp DATETIME,
    Temperature FLOAT,
    FeelsLike FLOAT,
    MinTemp FLOAT,
    MaxTemp FLOAT,
    Humidity INT,
    Pressure INT,
    WindSpeed FLOAT,
    WindDeg INT,
    WeatherDescription VARCHAR(255),
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

-- Create a table to store air pollution data
CREATE TABLE AirPollution (
    PollutionID INT PRIMARY KEY IDENTITY(1,1),
    LocationID INT,
    Timestamp DATETIME,
    AQI INT,
    CO FLOAT,
    NO FLOAT,
    NO2 FLOAT,
    O3 FLOAT,
    SO2 FLOAT,
    NH3 FLOAT,
    PM2_5 FLOAT,
    PM10 FLOAT,
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

