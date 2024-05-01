Overview
----------------------------------------------------------
Weather Application built using JAVA.

GUI has been designed using the JavaFX Library.
The Relational Database of MySQL has been used for Storing Data.

----------------------------------------------------------

The Application uses Open Weather's free-to-use APIs to fetch Current Weather data, data for a 5-day Forecast, and data for Air Pollution using *CurrentWeatherAPI*, *WeatherForecast5dayAPI*, and *AirPollutionAPI*.
Implementations have been given in *"Src/OpenWeatherAPI"*. Documentation can be found here https://openweathermap.org/api

*"Src/OpenWeatherAPI"* Contains a folder of *ApiJSON* which shows sample JSON responses for each of the APIs.

*InterfaceAPI* includes a common interface being implemented by each of the APIs.

----------------------------------------------------------


**Folder Structure**
----------------------------------------------------------


*Project_UML* : Contains all the UML diagrams for designing the Application, including the Sequence and Class Diagrams.

*Src* : Contains all the Source Code for the Application.

----------------------------------------------------------

**Src**
----------------------------------------------------------

*AppUI* : Contains the Source code for Implementing individual User Interfaces (Terminal UI and GUI).

*Business Logic* : Holds the Source code and core logic of the exchange of information between the type of database (txt or SQL) and user interface.

*OpenWeatherAPI* :  Has the Source code for calling each of the individual APIs.

*WeatherAppRunner* :  Has the Source code and the main Control for Running the Application.

*WeatherDataStorage* :  Has the Source code for managing the SQL connection, SQL commands for creating tables, Folder for creating Schemas as-well-as Logic for fetching Data Through the SQL Database/TxT File.





