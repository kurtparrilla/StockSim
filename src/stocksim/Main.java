/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package stocksim;
import java.util.Scanner;
import java.io.Console;
import java.util.Arrays;
/**
 *
 * @author nyx
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String username;
        String password;
        while(true){
            System.out.println("========================");
            System.out.println("=======Welcome to=======");
            System.out.println("====TradingSimulator====");
            System.out.println("========================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Option: ");
            int option = scan.nextInt();
            scan.nextLine();
            if(option == 1){
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print("Enter username: ");
                    username = scan.nextLine();
                    Console con = System.console();
                    if(con == null){
                        System.out.println("Please run this program from a terminal.");
                        return;
                    }
                    char[] passwordChar = con.readPassword("Password: ");
                    password = new String(passwordChar);
                    Arrays.fill(passwordChar, '\0');
            }else if(option == 2){
                System.out.println("Option 2");
            }else{
                System.out.println("The available options are 1 and 2 only!");
            }
        }

    }
    
}
