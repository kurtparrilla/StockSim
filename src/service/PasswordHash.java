/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author nyx
 */
public class PasswordHash {
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    public static Boolean verifyPassword(String password, String storedHashed){
        return BCrypt.checkpw(password, storedHashed);
    }
}
