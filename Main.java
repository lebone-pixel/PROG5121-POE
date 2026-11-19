import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        Message message = new Message();

        // load any previously stored messages
        message.loadStoredMessages();

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

        // ========== PART 2 & 3 – QUICKCHAT ==========
        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;
        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages (Coming Soon)");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("How many messages do you want to send? ");
                int numMessages = sc.nextInt();
                sc.nextLine();

                for (int i = 1; i <= numMessages; i++) {
                    System.out.println("\n--- Message " + i + " of " + numMessages + " ---");

                    String msgID = message.generateMessageID();
                    System.out.println("Message ID: " + msgID);

                    System.out.print("Enter recipient cell number: ");
                    String recipient = sc.nextLine();
                    String cellCheck = message.checkRecipientCell(recipient);
                    System.out.println(cellCheck);

                    if (!cellCheck.contains("successfully")) {
                        continue;
                    }

                    System.out.print("Enter your message (max 250 characters): ");
                    String text = sc.nextLine();

                    if (text.length() > 250) {
                        System.out.println("Message exceeds 250 characters by " + (text.length() - 250) + ", please reduce the size.");
                        continue;
                    }

                    String hash = message.createMessageHash(msgID, i, text);
                    System.out.println("Message Hash: " + hash);

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
                        message.addDisregardedMessage(text);
                        System.out.println("Message disregarded.");
                    } else if (action == 3) {
                        message.storeMessage(msgID, hash, recipient, text);
                    }
                }

                System.out.println("\nTotal messages sent: " + message.returnTotalMessages());

            } else if (choice == 2) {
                System.out.println("Coming Soon.");

            } else if (choice == 3) {
                System.out.println("Thank you for using QuickChat. Goodbye!");
                running = false;

            } else if (choice == 4) {
                // PART 3 menu
                System.out.println("\n--- Stored Messages Menu ---");
                System.out.println("a) Display all stored messages");
                System.out.println("b) Display the longest message");
                System.out.println("c) Search by Message ID");
                System.out.println("d) Search by recipient");
                System.out.println("e) Delete a message by hash");
                System.out.println("f) Full report");
                System.out.print("Enter your choice (a-f): ");
                String sub = sc.nextLine().toLowerCase();

                if (sub.equals("a")) {
                    message.displayAllStored();
                } else if (sub.equals("b")) {
                    message.displayLongestMessage();
                } else if (sub.equals("c")) {
                    System.out.print("Enter Message ID: ");
                    String id = sc.nextLine();
                    message.searchByID(id);
                } else if (sub.equals("d")) {
                    System.out.print("Enter recipient number: ");
                    String rec = sc.nextLine();
                    message.searchByRecipient(rec);
                } else if (sub.equals("e")) {
                    System.out.print("Enter Message Hash: ");
                    String hash = sc.nextLine();
                    message.deleteByHash(hash);
                } else if (sub.equals("f")) {
                    message.displayFullReport();
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
}
