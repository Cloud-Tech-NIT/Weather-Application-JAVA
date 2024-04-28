
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.Statement;
// import javax.swing.JOptionPane;
// import com.mysql.cj.xdevapi.PreparableStatement;

// //-- Description:
// //this java file creates weather_Cache DB to store all the data
// //** THERE IS NO NEED TO ADD THIS TO MAIN PROGRAM FILE SINCE THIS WAS ONLY TO
// SHOW THAT A NEW DATABASE HAS BEEN CREATED FOR THE APP */
// //** THE DATABASE IS INDEPENDENT OF JAVA PROGRAM AND CAN BE ACCESSED AND
// MODIFIED BY ANYONE WHO HAS THE USERNAME+ PASS FOR THE SQL SERVER*/
// ** To view the database go to MYSQL COMMAND LINE and enter "show databases;"
// */

// public class DBCreate {
// public void createDatabase() {
// try {
// String url = "jdbc:mysql://localhost:3306/";

// String databaseName = "weather_Cache";
// String userName = "root";
// String password = "abc_123";

// Connection connection = DriverManager.getConnection(url, userName, password);
// Statement stm = connection.createStatement();

// String sql = "CREATE DATABASE " + databaseName;

// Statement statement = connection.createStatement();
// statement.executeUpdate(sql);
// statement.close();
// JOptionPane.showMessageDialog(null, databaseName + " Database has been
// created successfully",
// "System Message", JOptionPane.INFORMATION_MESSAGE);

// } catch (Exception e) {
// e.printStackTrace();
// }

// }

// }
