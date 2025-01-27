import java.util.Arrays;

public class Parser {
    public static void parse(String input, UserInterface ui, TaskList tasks, Storage storage) {
        String cmd = input.split(" ")[0];
        while (!input.startsWith("bye")) {
            try {
                switch (cmd) {
                case "list" -> {
                    ui.reply("Here are your tasks:", Arrays.toString(tasks.toStringList().toArray(new String[0])));
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
                default -> {
                    throw new BaymaxException("I cannot comprehend what you are saying.");
                }
                }
            } catch (BaymaxException e) {
                ui.reply(e.getMessage());
            } finally {
                input = ui.getInput();
                cmd = input.split(" ")[0];
            }
        }
    }
}
