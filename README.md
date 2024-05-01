******Weather Application in Java******
--------------------------------------------
--------------------------------------------

****Overview****
--------------------------------------------

The Weather Application is a Java-based software solution designed to provide users with real-time weather data and forecasts. Leveraging JavaFX for its graphical user interface and MySQL for efficient data storage, this application offers a seamless user experience with robust data management capabilities.

****Features****
--------------------------------------------

JavaFX GUI: Utilizes the JavaFX library to create an intuitive and visually appealing user interface.

Relational Database: Employs MySQL to store and manage weather data efficiently, ensuring scalability and reliability.

Open Weather APIs: Integrates Open Weather's free-to-use APIs to fetch current weather data, 5-day forecasts, and air pollution information. The application utilizes CurrentWeatherAPI, 

WeatherForecast5dayAPI, and AirPollutionAPI for data retrieval. Comprehensive documentation for these APIs can be found here.

InterfaceAPI: Implements a common interface across all APIs, enhancing modularity and maintainability.

****Folder Structure****
--------------------------------------------

Project_UML: Contains UML diagrams including Sequence and Class diagrams, providing insights into the application's design and architecture.

Src: Houses all the source code for the application.


****Src****
--------------------------------------------

AppUI: Implements individual user interfaces, including terminal UI and GUI, ensuring versatility in user interaction.

Business Logic: Houses core logic for data exchange between the database (txt or SQL) and user interface, ensuring seamless communication and data integrity.

OpenWeatherAPI: Contains source code for calling Open Weather APIs, facilitating real-time data retrieval and processing.

WeatherAppRunner: Serves as the main control for running the application, orchestrating various components for seamless execution.

WeatherDataStorage: Manages SQL connections, SQL commands for table creation, and schema creation logic. Additionally, it handles data retrieval from SQL databases and txt files, ensuring efficient data management and accessibility.

****Usage****
--------------------------------------------

To run the Weather Application:

Clone the repository to your local machine.
Navigate to the project directory.
Execute the WeatherAppRunner class.
Follow the on-screen instructions to interact with the application and access weather information.

**Acknowledgments**
--------------------------------------------

Open Weather for providing free-to-use APIs for weather data.
JavaFX and MySQL communities for their valuable libraries and resources.
