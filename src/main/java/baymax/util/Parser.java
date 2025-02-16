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
import baymax.command.SortCommand;
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

    private static final String[] patterns = {
        "yyyy-MM-dd HH:mm", //e.g. 2025-01-27 12:30
        "dd/MM/yyyy HH:mm", //e.g. 27/01/2025 12:30
        "yyyy MM dd HH:mm", //e.g. 2025 01 27 12:30
    };
    /**
     * Parses the user input and returns the appropriate {@code Command}.
     *
     * @param input The user input string.
     * @return A {@code Command} object that corresponds to the given input.
     * @throws BaymaxException If the input command is invalid or improperly formatted.
     */
    public static Command parse(String input) throws BaymaxException {
        String[] args = input.split(" ");
        switch (args[0].toLowerCase()) {
        case "list" -> {
            return new ListCommand();
        }
        case "mark" -> {
            checkArgsExists(args, "Do let me know which task to mark/unmark.");
            return new MarkCommand(Integer.parseInt(args[1]) - 1);
        }
        case "unmark" -> {
            checkArgsExists(args, "Do let me know which task to mark/unmark.");
            return new UnmarkCommand(Integer.parseInt(args[1]) - 1);
        }
        case "todo" -> {
            checkArgsExists(args, "Let me know what task you wish to add.");
            return createTodoCommand(input);
        }
        case "deadline" -> {
            checkArgsExists(args, "Let me know what task you wish to add.");
            return createDeadlineCommand(input);
        }
        case "event" -> {
            checkArgsExists(args, "Let me know what task you wish to add.");
            return createEventCommand(input);
        }
        case "delete" -> {
            checkArgsExists(args, "Let me know what task you are looking for.");
            return new DeleteCommand(Integer.parseInt(args[1]) - 1);
        }
        case "bye" -> {
            return new ByeCommand();
        }
        case "hello" -> {
            return new HelloCommand();
        }
        case "find" -> {
            checkArgsExists(args, "Let me know what task you are looking for.");
            return new FindCommand(args[1]);
        }
        case "sort" -> {
            return new SortCommand();
        }
        default -> {
            throw new BaymaxException("I cannot comprehend what you are saying.");
        }
        }
    }
    /**
     * Creates an AddTodoCommand with the given input string.
     *
     * @param input The input string containing the task description.
     * @return An AddTodoCommand containing a new Todo task.
     */
    private static AddTodoCommand createTodoCommand(String input) {
        String taskDescription = input.substring(input.indexOf(" ") + 1);
        return new AddTodoCommand(new Todo(taskDescription));
    }

    /**
     * Creates an AddDeadlineCommand with the given input string.
     *
     * @param input The input string containing the task description and deadline.
     * @return An AddDeadlineCommand containing a new Deadline task.
     * @throws BaymaxException If the deadline parameter is missing.
     */
    private static AddDeadlineCommand createDeadlineCommand(String input) throws BaymaxException {
        int byIdx = getParamIndex(input, "/by ", "Let me know the deadline of the task.");
        String taskDescription = input.substring(input.indexOf(" ") + 1, byIdx - 1);
        String deadlineDate = input.substring(byIdx + 4);
        return new AddDeadlineCommand(new Deadline(taskDescription, Parser.parseDateTime(deadlineDate)));
    }

    /**
     * Creates an AddEventCommand with the given input string.
     *
     * @param input The input string containing the task description, start time, and end time.
     * @return An AddEventCommand containing a new Event task.
     * @throws BaymaxException If the start or end time parameters are missing.
     */
    private static AddEventCommand createEventCommand(String input) throws BaymaxException {
        int fromIdx = getParamIndex(input, "/from ", "Let me know when the event starts.");
        int toIdx = getParamIndex(input, "/to ", "Let me know when the event ends.");
        String taskDescription = input.substring(input.indexOf(" ") + 1, fromIdx - 1);
        String fromDate = input.substring(fromIdx + 6, toIdx - 1);
        String toDate = input.substring(toIdx + 4);
        return new AddEventCommand(new Event(taskDescription, Parser.parseDateTime(fromDate),
                Parser.parseDateTime(toDate)));
    }

    /**
     * Checks if the required arguments exist in the input array.
     * Throws a BaymaxException if the number of arguments is insufficient.
     *
     * @param args The array of command arguments.
     * @param errorMsg The error message to display if arguments are missing.
     * @throws BaymaxException If the arguments length is less than 2.
     */
    private static void checkArgsExists(String[] args, String errorMsg) throws BaymaxException {
        if (args.length < 2) {
            throw new BaymaxException(errorMsg);
        }
    }
    /**
     * Finds the index of a specified parameter indicator within the input string.
     * If the parameter indicator is not found, throws a {@code BaymaxException} with the provided error message.
     *
     * @param input The input string to search within.
     * @param paramIndicator The substring representing the parameter indicator (e.g., "/by ", "/from ").
     * @param errorMsg The error message to be thrown if the parameter indicator is not found.
     * @return The index of the parameter indicator within the input string.
     * @throws BaymaxException If the parameter indicator is not found in the input string.
     */
    private static int getParamIndex(String input, String paramIndicator, String errorMsg) throws BaymaxException {
        int targetIdx = input.indexOf(paramIndicator);
        if (targetIdx < 0) {
            throw new BaymaxException(errorMsg);
        }
        return targetIdx;
    }
    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTimeStr The date-time string to be parsed.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws BaymaxException If the date-time string does not match any valid format.
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws BaymaxException {
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
