/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stocksim.ui;

import java.io.Console;
import java.sql.SQLException;
import java.util.Arrays;
import service.AuthService;
import stocksim.model.User;

/**
 *
 * @author nyx
 */
public class ConsoleApp {
    public void run(){
        String username;
        String password;
        Console con = System.console();
        if(con == null){
            System.out.println("Please run this program from a terminal.");
            return;
        }
        AuthService as = new AuthService();
        while(true){
            clearScreen();
            System.out.println("========================");
            System.out.println("        STOCKSIM        ");
            System.out.println("Virtual Stock Exchange ");
            System.out.println("========================");
            System.out.println("[1]. Login");
            System.out.println("[2]. Register");
            System.out.println("[0]. Exit");
            String option = con.readLine("Choose an option: ");
            if(option == null){
                return;
            }
            if(option.equals("1")){
                clearScreen();
                System.out.println("-----------------");
                System.out.println("      LOGIN      ");
                System.out.println("-----------------");
                username = con.readLine("Enter username: ");
                if(username == null){
                    return;
                }
                char[] passwordChar = con.readPassword("Password: ");
                if(passwordChar == null){
                    return;
                }
                password = new String(passwordChar);
                Arrays.fill(passwordChar, '\0');
                try {
                    User user = as.login(username, password);
                    if(user != null){
                        showDashboard(user, con);
                    }else{
                        System.out.println("Invalid username or password.");
                        pauseForEnter(con);
                    }
                } catch (SQLException e) {
                    System.out.println("Login failed"); 
                    System.out.println(e.getMessage());
                    pauseForEnter(con);
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    pauseForEnter(con);
                }
            }else if(option.equals("2")){
                clearScreen();
                String password2;
                System.out.println("-----------------");
                System.out.println("   REGISTRATION  ");
                System.out.println("-----------------");
                username = con.readLine("Enter username: ");
                if(username == null){
                    return;
                }
                char[] passwordChar = con.readPassword("Password: ");
                if(passwordChar == null){
                    return;
                }
                password = new String(passwordChar);
                Arrays.fill(passwordChar, '\0');
                passwordChar = con.readPassword("Confirm Password: ");
                password2 = new String(passwordChar);
                Arrays.fill(passwordChar, '\0');
                if(password.equals(password2)){
                    try {
                        as.register(username, password);
                        System.out.println("Your account has been registered.");
                        pauseForEnter(con);
                    } catch (SQLException ex) {
                        System.out.println("Registration error your username might already been used.");
                        pauseForEnter(con);
                        //System.out.println(ex.getMessage());
                    } catch (IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                        pauseForEnter(con);
                    }
                }else{
                    System.out.println("Password does not match.");
                    pauseForEnter(con);
                }
            }else if(option.equals("0")){
                System.out.println("Goodbye and thank you for using our services. WE LOVE YOU");
                System.exit(0);
            }
            else{
                System.out.println("The available options are 1,2 and 0 only!");
                pauseForEnter(con);
            }
        }
    }
    public void showDashboard(User user, Console con){
        while(true){
            clearScreen();
            System.out.println("========================");
            System.out.println("   STOCKSIM DASHBOARD   ");
            System.out.println("========================");
            System.out.println("Welcome, " + user.getUsername());
            System.out.println("[1]. View wallet");
            System.out.println("[2]. View stocks");
            System.out.println("[3]. Logout");
            String option = con.readLine("Choose an option: ");
            if(option == null){
                return;
            }
            switch(option){
                case "1":
                    clearScreen();
                    System.out.println("Wallet balance: $" + user.getWalletBalance());
                    System.out.println("Available cash: $" + user.getAvailableCash());
                    System.out.println("Reserved cash: $" + user.getReservedCash());
                    pauseForEnter(con);
                    break;
                case "2":
                    clearScreen();
                    System.out.println("View stocks");
                    pauseForEnter(con);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Valid options are 1 and 2 only!");
                    pauseForEnter(con);
                    break;
            }
        }

    }
    public void pauseForEnter(Console cs){
        cs.readLine("\nPress Enter to return to the menu");
    }
    public void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
