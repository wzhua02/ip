package baymax.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;

class UiTest {
    private Ui ui;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        inputStream = new ByteArrayInputStream("test input\n".getBytes());
        ui = new Ui(inputStream, printStream);
    }

    @Test
    void testGetInput() {
        assertEquals("test input", ui.getInput());
    }

    @Test
    void testReply() {
        ui.reply("Hello, world!", "This is a test.");
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Hello, world!"));
        assertTrue(output.contains("This is a test."));
        assertTrue(output.contains("_") && output.indexOf("_") < output.indexOf("Hello, world!"));
    }
}

