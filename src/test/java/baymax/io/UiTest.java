package baymax.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class UiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        Ui.initializeUi();
    }

    @Test
    void testReply() {
        Ui.reply("Hello", "World!");
        String expectedOutput = "_".repeat(50) + "\n" +
                Ui.INDENT + "Hello\n" +
                Ui.INDENT + "World!\n" +
                "_".repeat(50) + "\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testGetInput() {
        String simulatedInput = "Test input\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        assertEquals("Test input", Ui.getInput());
    }
}
