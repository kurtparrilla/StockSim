/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import Database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author nyx
 */
public class AuthService {
    public void register(String username, String password) throws SQLException{
        if(username == null || username.trim().isEmpty() || username.length() > 50){
            throw new IllegalArgumentException("Invalid username");
        }
        if(password == null || password.trim().isEmpty() || password.length() < 8){
            throw new IllegalArgumentException("Invalid password or less than 8 characters");
        }
        String passwordHash = PasswordHash.hashPassword(password);
        try(Connection conn = DatabaseConnection.getConnection()){
            try(PreparedStatement ptst = conn.prepareStatement("INSERT INTO Users (username, password_hash) VALUES (?,?)")){
                ptst.setString(1, username);
                ptst.setString(2, passwordHash);
                ptst.executeUpdate();
            }
        }
    }
  
    public boolean login(String username, String password) throws SQLException{
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Invalid username");
        }
        if(password == null || password.trim().isEmpty()){
            throw new IllegalArgumentException("Invalid password");
        }
        try(Connection conn = DatabaseConnection.getConnection()){
            try(PreparedStatement pstp = conn.prepareStatement("SELECT password_hash FROM Users WHERE username = ?")){
                pstp.setString(1, username);
                try(ResultSet rs = pstp.executeQuery()){
                    if(!rs.next()){
                        return false;
                    }
                    String storedHash = rs.getString("password_hash");
                    return PasswordHash.verifyPassword(password, storedHash);
                }
            }
        }
    }
}
