package baymax.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import baymax.exception.BaymaxException;
import baymax.task.TaskList;
import baymax.task.Todo;

class ParserTest {

    @Test
    void parseDateTime_validPatterns_parsedCorrectly() throws BaymaxException {
        assertEquals(LocalDateTime.of(2025, 1, 27, 12, 30),
                Parser.parseDateTime("2025-01-27 12:30"));

        assertEquals(LocalDateTime.of(2025, 1, 27, 12, 30),
                Parser.parseDateTime("27/01/2025 12:30"));

        assertEquals(LocalDateTime.of(2025, 1, 27, 12, 30),
                Parser.parseDateTime("2025 01 27 12:30"));
    }

    @Test
    void parseDateTime_invalidPattern_exceptionThrown() {
        BaymaxException exception = assertThrows(BaymaxException.class, () ->
                Parser.parseDateTime("01-27-2025 12:30")); // Invalid pattern

        assertTrue(exception.getMessage().contains("No valid date-time pattern found"));
    }
    @Test
    void parse_listCommand_tasksListed() {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        String input = "list";
        String expectedOutput = "Here are your tasks:\n";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_markCommand_taskMarked() throws BaymaxException {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        tasks.addTask(new Todo("Sample Task"));
        String input = "mark 1";
        String expectedOutput = "Okie dokie this is marked as done:\n[T][X] Sample Task";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_unmarkCommand_taskUnmarked() throws BaymaxException {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        tasks.addTask(new Todo("Sample Task"));
        tasks.getTask(0).marker(true); // Mark the task first
        String input = "unmark 1";
        String expectedOutput = "Okie this is marked as not done yet:\n[T][ ] Sample Task";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_todoCommand_taskAdded() {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        String input = "todo Sample Task";
        String expectedOutput = "Got it. Added this task:\n[T][ ] Sample Task\nNow you have 1 tasks in the list.";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_deadlineCommand_taskAdded() {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        String input = "deadline Sample Task /by 2025-01-27 12:30";
        String expectedOutput = "Got it. Added this task:\n[D][ ] Sample Task (by: Jan 27 2025 12:30)\nNow you have 1 tasks in the list.";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_eventCommand_taskAdded() {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        String input = "event Sample Event /from 2025-01-27 12:30 /to 2025-01-27 14:30";
        String expectedOutput = "Got it. Added this task:\n[E][ ] Sample Event (from: Jan 27 2025 12:30 to: Jan 27 2025 14:30)\nNow you have 1 tasks in the list.";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_deleteCommand_taskRemoved() throws BaymaxException {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        tasks.addTask(new Todo("Sample Task"));
        String input = "delete 1";
        String expectedOutput = "Task removed!\n   [T][ ] Sample Task\nNow you have 0 tasks in the list.";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_findCommand_tasksFound() throws BaymaxException {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        tasks.addTask(new Todo("Sample Task"));
        tasks.addTask(new Todo("Another Task"));
        String input = "find Sample";
        String expectedOutput = "These are the tasks you are looking for:\n1.[T][ ] Sample Task\n";
        assertEquals(expectedOutput, Parser.parse(input, tasks, storage));
    }

    @Test
    void parse_invalidCommand_exceptionThrown() {
        TaskList tasks = new TaskList();
        Storage storage = new Storage();
        String input = "invalid";
        BaymaxException exception = assertThrows(BaymaxException.class, () ->
                Parser.parse(input, tasks, storage));
        assertTrue(exception.getMessage().contains("I cannot comprehend what you are saying."));
    }
}

