/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stocksim.model;

import java.math.BigDecimal;

/**
 *
 * @author nyx
 */
public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private BigDecimal walletBalance;
    private BigDecimal reservedCash;
    public User(int userId, String username, String passwordHash, BigDecimal walletBalance, BigDecimal reservedCash){
        if(walletBalance == null || reservedCash == null){
            throw new IllegalArgumentException("Balance must not be null");
        }
        if(walletBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Wallet balance must not be negative.");
        }
        if(reservedCash.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Reserved cash must not be negative.");
        }
        if(reservedCash.compareTo(walletBalance) > 0){
            throw new IllegalArgumentException("Reserved cash must not exceed wallet balance.");
        }
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.walletBalance = walletBalance;
        this.reservedCash = reservedCash;
    }
    public BigDecimal getAvailableCash(){
        return walletBalance.subtract(reservedCash);
    }
    public int getUserId(){
        return userId;
    }
    public String getUsername(){
        return username;
    }
    public String getPasswordHash(){
        return passwordHash;
    }
    public BigDecimal getWalletBalance(){
        return walletBalance;
    }
    public BigDecimal getReservedCash(){
        return reservedCash;
    }
}
