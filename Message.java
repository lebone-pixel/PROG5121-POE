import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Message {

    // arrays for different types of messages
    private ArrayList<String> sentMessages = new ArrayList<>();
    private ArrayList<String> disregardedMessages = new ArrayList<>();
    private ArrayList<String> storedMessages = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> recipients = new ArrayList<>();
    private ArrayList<String> fullMessages = new ArrayList<>(); // keeps the actual text

    private int totalMessagesSent = 0;

    // ---------- checkMessageID ----------
    public boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    // ---------- checkRecipientCell ----------
    public String checkRecipientCell(String cell) {
        if (cell.startsWith("+") && cell.length() <= 13) {
            String numbers = cell.substring(1);
            for (int i = 0; i < numbers.length(); i++) {
                if (!Character.isDigit(numbers.charAt(i))) {
                    return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
                }
            }
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // ---------- createMessageHash ----------
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String hash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // ---------- generateMessageID ----------
    public String generateMessageID() {
        Random rand = new Random();
        long number = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    // ---------- add a sent message ----------
    public void addSentMessage(String id, String hash, String recipient, String text) {
        messageIDs.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);
        fullMessages.add(text);
        sentMessages.add(text);
        totalMessagesSent++;
    }

    // ---------- add a disregarded message ----------
    public void addDisregardedMessage(String text) {
        disregardedMessages.add(text);
    }

    // ---------- store message to JSON ----------
    public void storeMessage(String id, String hash, String recipient, String text) {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true);
            writer.write(id + "|" + hash + "|" + recipient + "|" + text + "\n");
            writer.close();
            storedMessages.add(text);
            messageIDs.add(id);
            messageHashes.add(hash);
            recipients.add(recipient);
            fullMessages.add(text);
            System.out.println("Message successfully stored.");
        } catch (IOException e) {
            System.out.println("Error saving message.");
        }
    }

    // ---------- read the JSON/text file into arrays ----------
    public void loadStoredMessages() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("storedMessages.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    messageIDs.add(parts[0]);
                    messageHashes.add(parts[1]);
                    recipients.add(parts[2]);
                    fullMessages.add(parts[3]);
                    storedMessages.add(parts[3]);
                }
            }
            reader.close();
        } catch (IOException e) {
            // file might not exist yet – that’s fine
        }
    }

    // ---------- a. Display all stored messages (recipient + message) ----------
    public void displayAllStored() {
        if (storedMessages.size() == 0) {
            System.out.println("No stored messages.");
            return;
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            System.out.println("Recipient: " + recipients.get(i));
            System.out.println("Message: " + fullMessages.get(i));
            System.out.println("-------------------");
        }
    }

    // ---------- b. Display the longest stored message ----------
    public void displayLongestMessage() {
        if (fullMessages.size() == 0) {
            System.out.println("No messages.");
            return;
        }
        String longest = fullMessages.get(0);
        for (int i = 1; i < fullMessages.size(); i++) {
            if (fullMessages.get(i).length() > longest.length()) {
                longest = fullMessages.get(i);
            }
        }
        System.out.println("Longest message: " + longest);
    }

    // ---------- c. Search by Message ID ----------
    public void searchByID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                System.out.println("Recipient: " + recipients.get(i));
                System.out.println("Message: " + fullMessages.get(i));
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    // ---------- d. Search by recipient ----------
    public void searchByRecipient(String cell) {
        boolean found = false;
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(cell)) {
                System.out.println("Message: " + fullMessages.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for that recipient.");
        }
    }

    // ---------- e. Delete by hash ----------
    public void deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                System.out.println("Message: \"" + fullMessages.get(i) + "\" successfully deleted.");
                messageIDs.remove(i);
                messageHashes.remove(i);
                recipients.remove(i);
                fullMessages.remove(i);
                storedMessages.remove(i);
                return;
            }
        }
        System.out.println("Hash not found.");
    }

    // ---------- f. Full report ----------
    public void displayFullReport() {
        if (fullMessages.size() == 0) {
            System.out.println("No messages to report.");
            return;
        }
        for (int i = 0; i < fullMessages.size(); i++) {
            System.out.println("Message Hash: " + messageHashes.get(i));
            System.out.println("Recipient: " + recipients.get(i));
            System.out.println("Message: " + fullMessages.get(i));
            System.out.println("-------------------");
        }
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public String printMessages() {
        if (sentMessages.size() == 0) {
            return "No messages have been sent yet.";
        }
        String result = "";
        for (int i = 0; i < sentMessages.size(); i++) {
            result += "Message: " + sentMessages.get(i) + "\n";
        }
        return result;
    }
}
