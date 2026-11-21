/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stocksim.ui;

import java.io.Console;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import service.AuthService;

/**
 *
 * @author nyx
 */
public class ConsoleApp {
    public void run(){
                Scanner scan = new Scanner(System.in);
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
            System.out.print("Option: ");
            int option = scan.nextInt();
            scan.nextLine();
            if(option == 1){
                clearScreen();
                System.out.println("-----------------");
                System.out.println("      LOGIN      ");
                System.out.println("-----------------");
                System.out.print("Enter username: ");
                username = scan.nextLine();
                char[] passwordChar = con.readPassword("Password: ");
                password = new String(passwordChar);
                Arrays.fill(passwordChar, '\0');
                try {
                    if(as.login(username, password)){
                        System.out.println("Login successfully");
                        pauseForEnter(con);
                    }else{
                        System.out.println("Invalid username or password.");
                        pauseForEnter(con);
                    }
                } catch (SQLException e) {
                    System.out.println("Login failed"); 
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }else if(option == 2){
                clearScreen();
                String password2;
                System.out.println("-----------------");
                System.out.println("   REGISTRATION  ");
                System.out.println("-----------------");
                System.out.print("Enter username: ");
                username = scan.nextLine();
                char[] passwordChar = con.readPassword("Password: ");
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
                        System.out.println("Username already exist.");
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
            }else if(option == 0){
                System.out.println("Goodbye and thank you for using our services. WE LOVE YOU");
                System.exit(0);
            }
            else{
                
                System.out.println("The available options are 1 and 2 only!");
                pauseForEnter(con);
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
