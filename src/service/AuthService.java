/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.SQLException;
import stocksim.model.User;
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
        UserRepository ur = new UserRepository();
        ur.createUser(username, PasswordHash.hashPassword(password));
    }
 
    public User login(String username, String password) throws SQLException{
        if(username == null || username.trim().isEmpty()){
            return null;
        }
        if(password == null || password.trim().isEmpty()){
            return null;
        }
        UserRepository ur = new UserRepository();
        User user = ur.findByUsername(username);
        if(user == null || !PasswordHash.verifyPassword(password, user.getPasswordHash())){
            return null;
        }
        return user;
    }
}
