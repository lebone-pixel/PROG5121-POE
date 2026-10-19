import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        Message message = new Message();

        // ========== PART 1 – REGISTRATION & LOGIN ==========
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

        String regResult = login.registerUser(username, password, cell, firstName, lastName);
        System.out.println(regResult);

        // only continue if registration worked
        if (!regResult.contains("successfully")) {
            System.out.println("Registration failed. Program ending.");
            return;
        }

        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String loginUser = sc.nextLine();

        System.out.print("Enter password: ");
        String loginPass = sc.nextLine();

        boolean loggedIn = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            System.out.println("Login failed. Program ending.");
            return;
        }

        // ========== PART 2 – QUICKCHAT ==========
        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;
        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages (Coming Soon)");
            System.out.println("3) Quit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear the enter key

            if (choice == 1) {
                // ask how many messages the user wants to send
                System.out.print("How many messages do you want to send? ");
                int numMessages = sc.nextInt();
                sc.nextLine();

                // for loop to capture each message
                for (int i = 1; i <= numMessages; i++) {
                    System.out.println("\n--- Message " + i + " of " + numMessages + " ---");

                    // generate Message ID
                    String msgID = message.generateMessageID();
                    System.out.println("Message ID: " + msgID);

                    // recipient
                    System.out.print("Enter recipient cell number: ");
                    String recipient = sc.nextLine();
                    String cellCheck = message.checkRecipientCell(recipient);
                    System.out.println(cellCheck);

                    if (!cellCheck.contains("successfully")) {
                        System.out.println("Skipping this message because of bad number.");
                        continue;
                    }

                    // message text
                    System.out.print("Enter your message (max 250 characters): ");
                    String text = sc.nextLine();

                    if (text.length() > 250) {
                        System.out.println("Message exceeds 250 characters by " + (text.length() - 250) + ", please reduce the size.");
                        continue;
                    } else {
                        System.out.println("Message ready to send.");
                    }

                    // create hash
                    String hash = message.createMessageHash(msgID, i, text);
                    System.out.println("Message Hash: " + hash);

                    // ask what to do with the message
                    System.out.println("\nWhat do you want to do with this message?");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Enter choice: ");
                    int action = sc.nextInt();
                    sc.nextLine();

                    if (action == 1) {
                        message.addSentMessage(msgID, hash, recipient, text);
                        System.out.println("Message successfully sent.");
                    } else if (action == 2) {
                        System.out.println("Press 0 to delete the message.");
                        // just ignore it
                    } else if (action == 3) {
                        message.storeMessage(msgID, hash, recipient, text);
                    } else {
                        System.out.println("Invalid choice, message disregarded.");
                    }
                }

                // after all messages
                System.out.println("\nTotal messages sent: " + message.returnTotalMessages());
                System.out.println("\nHere are the messages that were sent:");
                System.out.println(message.printMessages());

            } else if (choice == 2) {
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                System.out.println("Thank you for using QuickChat. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option, please try again.");
            }
        }

        sc.close();
    }
}
