package baymax.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
import baymax.task.Deadline;
import baymax.task.Event;
import baymax.task.Todo;

/**
 * The Parser class is responsible for parsing user input and executing the corresponding commands.
 */
public class Parser {
    private static final String INDENT = "    ";
    /**
     * Parses the user input and returns the appropriate {@code Command}.
     *
     * @param input The user input string.
     * @return A {@code Command} object that corresponds to the given input.
     * @throws BaymaxException If the input command is invalid or improperly formatted.
     */
    public static Command parse(String input) throws BaymaxException {
        String[] args = input.split(" ");
        String cmd = args[0].toLowerCase();

        switch (cmd) {
        case "list" -> {
            return new ListCommand();
        }
        case "mark" -> {
            return new MarkCommand(Integer.parseInt(args[1]) - 1);
        }
        case "unmark" -> {
            return new UnmarkCommand(Integer.parseInt(args[1]) - 1);
        }
        case "todo" -> {
            int spaceIdx = input.indexOf(" ");
            if (spaceIdx < 0) {
                throw new BaymaxException("Let me know what task you wish to add.");
            }
            String taskDescribe = input.substring(spaceIdx + 1);
            Todo newTodo = new Todo(taskDescribe);
            return new AddTodoCommand(newTodo);
        }
        case "deadline" -> {
            int spaceIdx = input.indexOf(" ");
            if (spaceIdx < 0) {
                throw new BaymaxException("Let me know what task you wish to add.");
            }
            int byIdx = input.indexOf("/by");
            if (byIdx < 0) {
                throw new BaymaxException("Let me know the deadline of the task.");
            }
            String taskDescribe = input.substring(spaceIdx + 1, byIdx - 1);
            String deadlineString = input.substring(byIdx + 4);
            Deadline newDeadline = new Deadline(taskDescribe, Parser.parseDateTime(deadlineString));
            return new AddDeadlineCommand(newDeadline);
        }
        case "event" -> {
            int spaceIdx = input.indexOf(" ");
            if (spaceIdx < 0) {
                throw new BaymaxException("Let me know what task you wish to add.");
            }
            int fromIdx = input.indexOf("/from");
            int toIdx = input.indexOf("/to");
            if (fromIdx < 0 || toIdx < 0) {
                throw new BaymaxException("Let me know when the event starts and ends.");
            }
            String taskDescribe = input.substring(spaceIdx + 1, fromIdx - 1);
            String fromDate = input.substring(fromIdx + 6, toIdx - 1);
            String toDate = input.substring(toIdx + 4);
            Event newEvent = new Event(taskDescribe, Parser.parseDateTime(fromDate), Parser.parseDateTime(toDate));
            return new AddEventCommand(newEvent);
        }
        case "delete" -> {
            int idx = Integer.parseInt(args[1]) - 1;
            return new DeleteCommand(idx);
        }
        case "bye" -> {
            return new ByeCommand();
        }
        case "hello" -> {
            return new HelloCommand();
        }
        case "find" -> {
            return new FindCommand(args[1]);
        }
        default -> {
            throw new BaymaxException("I cannot comprehend what you are saying.");
        }
        }
    }
    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTimeStr The date-time string to be parsed.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws BaymaxException If the date-time string does not match any valid format.
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws BaymaxException {
        String[] patterns = {
            "yyyy-MM-dd HH:mm", //e.g. 2025-01-27 12:30
            "dd/MM/yyyy HH:mm", //e.g. 27/01/2025 12:30
            "yyyy MM dd HH:mm", //e.g. 2025 01 27 12:30
        };
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException e) {
                // Ignore and try the next pattern
            }
        }
        throw new BaymaxException("No valid date-time pattern found for: " + dateTimeStr
                + "\n" + INDENT + "Try to format the date-time in the following pattern:"
                + "\n" + INDENT + "e.g. 2025-01-27 12:30"
                + "\n" + INDENT + INDENT + " 27/01/2025 12:30"
                + "\n" + INDENT + INDENT + " 2025 01 27 12:30");
    }
}
