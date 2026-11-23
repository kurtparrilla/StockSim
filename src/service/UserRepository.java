/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import stocksim.model.User;
/**
 *
 * @author nyx
 */
public class UserRepository {
    public User findByUsername(String username) throws SQLException{
        try(Connection conn = DatabaseConnection.getConnection()){
            try(PreparedStatement pstd = conn.prepareStatement("SELECT user_id, username, password_hash, wallet_balance, reserved_cash"
                    + " FROM Users WHERE username = ?")){
                pstd.setString(1, username);
                try(ResultSet rs = pstd.executeQuery()){
                    if(!rs.next()){
                        return null;
                    }
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getBigDecimal("wallet_balance"),
                        rs.getBigDecimal("reserved_cash")
                    );
                }
            }
        }
    }
    public void createUser(String username, String passwordHash) throws SQLException{
        try(Connection conn = DatabaseConnection.getConnection()){
            try(PreparedStatement ptst = conn.prepareStatement("INSERT INTO Users (username, password_hash) VALUES (?,?)")){
                ptst.setString(1, username);
                ptst.setString(2, passwordHash);
                ptst.executeUpdate();
            }
        }
    }
}
