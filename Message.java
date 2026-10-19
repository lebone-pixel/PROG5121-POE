import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    // these keep track of all messages while the program is running
    private ArrayList<String> messageIDs = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> recipients = new ArrayList<>();
    private ArrayList<String> messages = new ArrayList<>();

    private int totalMessagesSent = 0;   // counts how many were actually sent

    // ---------- checkMessageID ----------
    // makes sure the ID is not longer than 10 characters
    public boolean checkMessageID(String id) {
        if (id.length() <= 10) {
            return true;
        }
        return false;
    }

    // ---------- checkRecipientCell ----------
    // must start with + and be 13 characters or less (e.g. +27831234567)
    public String checkRecipientCell(String cell) {
        if (cell.startsWith("+") && cell.length() <= 13) {
            // check the rest are only numbers
            String numbers = cell.substring(1);
            boolean allDigits = true;
            for (int i = 0; i < numbers.length(); i++) {
                if (!Character.isDigit(numbers.charAt(i))) {
                    allDigits = false;
                    break;
                }
            }
            if (allDigits) {
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // ---------- createMessageHash ----------
    // format: first two digits of ID : message number : FIRSTWORDLASTWORD (all capital)
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwo = messageID.substring(0, 2);

        // split the message into words
        String[] words = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // ---------- SentMessage ----------
    // lets the user choose what to do with the message
    public String SentMessage() {
        // this method is called after the user has typed the message
        // the actual choice is handled in Main, but we keep the method as required
        return "Message handled";
    }

    // ---------- printMessages ----------
    // shows all the messages that were sent
    public String printMessages() {
        if (messageIDs.size() == 0) {
            return "No messages have been sent yet.";
        }

        String result = "";
        for (int i = 0; i < messageIDs.size(); i++) {
            result = result + "Message ID: " + messageIDs.get(i) + "\n";
            result = result + "Message Hash: " + messageHashes.get(i) + "\n";
            result = result + "Recipient: " + recipients.get(i) + "\n";
            result = result + "Message: " + messages.get(i) + "\n";
            result = result + "------------------------\n";
        }
        return result;
    }

    // ---------- returnTotalMessages ----------
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // ---------- storeMessage (JSON) ----------
    // simple way to save one message into a JSON file
    public void storeMessage(String id, String hash, String recipient, String text) {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true); // true = append
            writer.write("{\n");
            writer.write("  \"MessageID\": \"" + id + "\",\n");
            writer.write("  \"MessageHash\": \"" + hash + "\",\n");
            writer.write("  \"Recipient\": \"" + recipient + "\",\n");
            writer.write("  \"Message\": \"" + text + "\"\n");
            writer.write("},\n");
            writer.close();
            System.out.println("Message successfully stored.");
        } catch (IOException e) {
            System.out.println("Error saving message to file.");
        }
    }

    // ---------- helper method used by Main ----------
    // this is called when the user chooses to SEND the message
    public void addSentMessage(String id, String hash, String recipient, String text) {
        messageIDs.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);
        messages.add(text);
        totalMessagesSent = totalMessagesSent + 1;
    }

    // generate a random 10-digit Message ID
    public String generateMessageID() {
        Random rand = new Random();
        long number = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }
}
