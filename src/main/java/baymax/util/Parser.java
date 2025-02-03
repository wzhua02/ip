package baymax.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import baymax.exception.BaymaxException;
import baymax.io.Storage;
import baymax.io.Ui;
import baymax.task.Deadline;
import baymax.task.Event;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.task.Todo;

/**
 * The Parser class is responsible for parsing user input and executing the corresponding commands.
 */
public class Parser {
    private static final String INDENT = "    ";

    /**
     * Parses the user input and executes the appropriate command.
     *
     * @param input   The user input string.
     * @param tasks   The TaskList containing user tasks.
     * @param storage The Storage handler for saving and loading tasks.
     */
    public static void parse(String input, TaskList tasks, Storage storage) {
        String[] args = input.split(" ");
        String cmd = args[0];
        try {
            switch (cmd) {
            case "list" -> {
                String replyLine = tasks.listTasks();
                Ui.reply("Here are your tasks:", replyLine);
            }
            case "mark" -> {
                if (args.length < 2) {
                    throw new BaymaxException("Do let me know which task to mark/unmark.");
                }
                int idx = Integer.parseInt(args[1]) - 1;
                if (idx < 0 || idx >= tasks.size()) {
                    throw new BaymaxException("I do not know which task you are referring to.");
                }
                Task theTask = tasks.getTask(idx);
                theTask.marker(true);
                tasks.save(storage);
                String markMsg = "Okie dokie this is marked as done:";
                Ui.reply(markMsg, "   " + theTask);
            }
            case "unmark" -> {
                if (args.length < 2) {
                    throw new BaymaxException("Do let me know which task to mark/unmark.");
                }
                int idx = Integer.parseInt(args[1]) - 1;
                if (idx < 0 || idx >= tasks.size()) {
                    throw new BaymaxException("I do not know which task you are referring to.");
                }
                Task theTask = tasks.getTask(idx);
                theTask.marker(false);
                tasks.save(storage);
                String markMsg = "Okie this is marked as not done yet:";
                Ui.reply(markMsg, "   " + theTask);
            }
            case "todo" -> {
                int spaceIdx = input.indexOf(" ");
                if (spaceIdx < 0) {
                    throw new BaymaxException("Let me know what task you wish to add.");
                }
                String taskDescribe = input.substring(spaceIdx + 1);
                Task newTask = new Todo(taskDescribe);
                tasks.addTask(newTask);
                tasks.save(storage);
                Ui.reply("Got it. Added this task:",
                        newTask.toString(),
                        "Now you have " + tasks.size() + " tasks in the list.");
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
                Task newTask = new Deadline(taskDescribe, Parser.parseDateTime(deadlineString));
                tasks.addTask(newTask);
                tasks.save(storage);
                Ui.reply("Got it. Added this task:",
                        newTask.toString(),
                        "Now you have " + tasks.size() + " tasks in the list.");
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
                Task newTask = new Event(taskDescribe, Parser.parseDateTime(fromDate), Parser.parseDateTime(toDate));
                tasks.addTask(newTask);
                tasks.save(storage);
                Ui.reply("Got it. Added this task:",
                        newTask.toString(),
                        "Now you have " + tasks.size() + " tasks in the list.");
            }
            case "delete" -> {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    throw new BaymaxException("Do let me know which task to mark/unmark.");
                }
                int idx = Integer.parseInt(parts[1]) - 1;
                if (idx < 0 || idx >= tasks.size()) {
                    throw new BaymaxException("I do not know which task you are referring to.");
                }
                Task theTask = tasks.getTask(idx);
                tasks.removeTask(theTask);
                tasks.save(storage);
                Ui.reply("Task removed!",
                        "   " + theTask,
                        "Now you have " + tasks.size() + " tasks in the list.");
            }
            case "bye" -> {
                // Do nothing
            }
            case "find" -> {
                if (args.length < 2) {
                    throw new BaymaxException("Let me know what task you would like to find.");
                }
                ArrayList<Task> foundTasks = tasks.findTask(args[1]);
                ArrayList<String> replyMsgs = new ArrayList<>();
                replyMsgs.add("These are the tasks you are looking for: ");
                for (Task task : foundTasks) {
                    replyMsgs.add(tasks.getTaskIdx(task) + ". " + task.toString());
                }
                Ui.reply(replyMsgs.toArray(new String[0]));
            }
            default -> {
                throw new BaymaxException("I cannot comprehend what you are saying.");
            }
            }
        } catch (BaymaxException e) {
            Ui.reply(e.getMessage());
        }
    }

    /**
     * Parse a command string. Yet to be implemented.
     *
     * @param input The user input string.
     */
    public static void parseCommand(String input) {
        String cmd = input.split(" ")[0];
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
