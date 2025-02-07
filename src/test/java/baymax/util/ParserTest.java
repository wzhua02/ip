package baymax.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import baymax.command.AddDeadlineCommand;
import baymax.command.AddEventCommand;
import baymax.command.AddTodoCommand;
import baymax.command.ByeCommand;
import baymax.command.Command;
import baymax.command.DeleteCommand;
import baymax.command.FindCommand;
import baymax.command.HelloCommand;
import baymax.command.ListCommand;
import baymax.command.MarkCommand;
import baymax.command.UnmarkCommand;
import baymax.exception.BaymaxException;

/**
 * JUnit tests for the {@code Parser} class.
 */
public class ParserTest {

    @Test
    public void parselistCommand_validInput_returnsListCommand() throws BaymaxException {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parsemarkCommand_validIndex_returnsMarkCommand() throws BaymaxException {
        Command command = Parser.parse("mark 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void parseunmarkCommand_validIndex_returnsUnmarkCommand() throws BaymaxException {
        Command command = Parser.parse("unmark 1");
        assertTrue(command instanceof UnmarkCommand);
    }

    @Test
    public void parsetodoCommand_validDescription_returnsAddTodoCommand() throws BaymaxException {
        Command command = Parser.parse("todo read book");
        assertTrue(command instanceof AddTodoCommand);
    }

    @Test
    public void parsedeadlineCommand_validInputs_returnsAddDeadlineCommand() throws BaymaxException {
        Command command = Parser.parse("deadline submit report /by 2025-12-01 23:59");
        assertTrue(command instanceof AddDeadlineCommand);
    }

    @Test
    public void parseeventCommand_validInputs_returnsAddEventCommand() throws BaymaxException {
        Command command = Parser.parse("event meeting /from 2025-12-01 10:00 /to 2025-12-01 12:00");
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    public void parsedeleteCommand_validIndex_returnsDeleteCommand() throws BaymaxException {
        Command command = Parser.parse("delete 1");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void parsehelloCommand_validInput_returnsHelloCommand() throws BaymaxException {
        Command command = Parser.parse("hello");
        assertTrue(command instanceof HelloCommand);
    }

    @Test
    public void parsebyeCommand_validInput_returnsByeCommand() throws BaymaxException {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ByeCommand);
    }

    @Test
    public void parsefindCommand_validKeyword_returnsFindCommand() throws BaymaxException {
        Command command = Parser.parse("find book");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    public void parseDateTime_validFormat_returnsLocalDateTime() throws BaymaxException {
        String dateTimeStr = "2025-01-27 12:30";
        LocalDateTime expected = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(expected, Parser.parseDateTime(dateTimeStr));
    }

    @Test
    public void parseDateTime_invalidFormat_throwsBaymaxException() {
        String invalidDate = "27-01-2025 12:30";
        assertThrows(BaymaxException.class, () -> Parser.parseDateTime(invalidDate));
    }
}
