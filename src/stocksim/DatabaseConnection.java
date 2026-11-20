/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stocksim;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author nyx
 */
public class DatabaseConnection {
       private static final String URL = System.getenv("STOCKSIM_DB_URL");
       private static final String USER = System.getenv("STOCKSIM_DB_USER");
       private static final String PASSWORD = System.getenv("STOCKSIM_DB_PASSWORD");
       public static Connection getConnection() throws SQLException{
           if(URL == null || USER == null || PASSWORD == null){
               throw new SQLException("Database environment variables are not configured");
           }
           return DriverManager.getConnection(URL, USER, PASSWORD);
       }
}
