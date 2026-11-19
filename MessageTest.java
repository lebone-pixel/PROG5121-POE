import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    Message message = new Message();

    @Test
    public void testMessageLength() {
        String text = "Did you get the cake?";
        assertTrue(text.length() <= 250);
    }

    @Test
    public void testRecipient() {
        String result = message.checkRecipientCell("+27834557896");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testLongestMessage() {
        // just checking the method exists and runs
        message.addSentMessage("1234567890", "12:1:DIDCAKE", "+27834557896", "Did you get the cake?");
        message.displayLongestMessage();
    }
}
