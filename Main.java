import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== REGISTRATION ===");
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter cell number (e.g. +27831234567): ");
        String cell = sc.nextLine();

        // call the register method
        String result = login.registerUser(username, password, cell, firstName, lastName);
        System.out.println(result);

        // only try login if registration worked
        if (result.contains("successfully")) {
            System.out.println("\n=== LOGIN ===");
            System.out.print("Enter username: ");
            String loginUser = sc.nextLine();

            System.out.print("Enter password: ");
            String loginPass = sc.nextLine();

            boolean success = login.loginUser(loginUser, loginPass);
            System.out.println(login.returnLoginStatus(success));
        }
    }
}
