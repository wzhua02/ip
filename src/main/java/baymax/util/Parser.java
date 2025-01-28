package baymax.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import baymax.exception.BaymaxException;
import baymax.io.Storage;
import baymax.io.Ui;
import baymax.task.Task;
import baymax.task.TaskList;

public class Parser {
    private static final String INDENT = "    ";

    public static void parse(String input, Ui ui, TaskList tasks, Storage storage) {
        String cmd = input.split(" ")[0];
        try {
            switch (cmd) {
            case "list" -> {
                ArrayList<String> replyList = tasks.toStringList();
                replyList.add(0, "Here are your tasks:");
                ui.reply(replyList.toArray(new String[0]));
            }
            case "mark", "unmark" -> {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    throw new BaymaxException("Do let me know which task to mark/unmark.");
                }
                int idx = Integer.parseInt(parts[1]) - 1;
                if (idx < 0 || idx >= tasks.size()) {
                    throw new BaymaxException("I do not know which task you are referring to.");
                }
                Task theTask = tasks.getTask(idx);
                theTask.marker(cmd.equals("mark"));
                tasks.save(storage);
                String markMsg = cmd.equals("mark")
                        ? "Okie dokie this is marked as done:"
                        : "Okie this is marked as not done yet:";
                ui.reply(markMsg, "   " + theTask);
            }
            case "todo", "deadline", "event" -> {
                Task newTask = tasks.addTask(input, cmd);
                tasks.save(storage);
                ui.reply("Got it. Added this task:",
                        newTask.toString(),
                        "Now you have " + tasks.size() + " tasks in the list.");
            }
            case "delete" -> {
                Task theTask = tasks.removeTask(input);
                tasks.save(storage);
                ui.reply("Task removed!",
                        "   " + theTask,
                        "Now you have " + tasks.size() + " tasks in the list.");
            }
            case "bye" -> {
                //do nothing
            }
            default -> {
                throw new BaymaxException("I cannot comprehend what you are saying.");
            }
            }
        } catch (BaymaxException e) {
            ui.reply(e.getMessage());
        }
    }
    public static void parseCommand(String input) {
        String cmd = input.split(" ")[0];
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) throws BaymaxException {
        String[] patterns = {
                "yyyy-MM-dd HH:mm",     //e.g. 2025-01-27 12:30
                "dd/MM/yyyy HH:mm",     //e.g. 27/01/2025 12:30
                "yyyy MM dd HH:mm",     //e.g. 2025 01 27 12:30
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
