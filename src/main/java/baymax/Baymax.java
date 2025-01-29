package baymax;

import baymax.io.Storage;
import baymax.io.Ui;
import baymax.task.TaskList;
import baymax.util.Parser;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The main class for the Baymax chatbot.
 * Handles user interactions and task management.
 */
public class Baymax {
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a Baymax instance.
     * @param filePath Path to the file where tasks are stored.
     */
    public Baymax(Path filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /**
     * Runs the chatbot, handling user input until "bye" is entered.
     */
    public void run() {
        ui.reply("Hello! I'm Baymax", "How can I assist you?");
        String input = ui.getInput();
        while (!input.startsWith("bye")) {
            Parser.parse(input, ui, tasks, storage);
            input = ui.getInput();
        }
        ui.reply("Goodbye! *slowly deflates*");
    }

    /**
     * The main method to start the chatbot.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Baymax(FILE_PATH).run();
    }
}
