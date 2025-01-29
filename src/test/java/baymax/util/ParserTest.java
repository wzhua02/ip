package baymax.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import baymax.exception.BaymaxException;
import org.junit.jupiter.api.Test;

class DateTimeParserTest {

    @Test
    void testValidDateTimePatterns() throws BaymaxException {
        assertEquals(LocalDateTime.of(2025, 1, 27, 12, 30),
                Parser.parseDateTime("2025-01-27 12:30"));

        assertEquals(LocalDateTime.of(2025, 1, 27, 12, 30),
                Parser.parseDateTime("27/01/2025 12:30"));

        assertEquals(LocalDateTime.of(2025, 1, 27, 12, 30),
                Parser.parseDateTime("2025 01 27 12:30"));
    }

    @Test
    void testInvalidDateTimePattern() {
        BaymaxException exception = assertThrows(BaymaxException.class, () ->
                Parser.parseDateTime("01-27-2025 12:30")); // Invalid pattern

        assertTrue(exception.getMessage().contains("No valid date-time pattern found"));
    }
}

