package baymax;

import java.nio.file.Path;
import java.nio.file.Paths;

import baymax.io.Storage;
import baymax.io.Ui;
import baymax.task.TaskList;
import baymax.util.Parser;

/**
 * The main class for the Baymax chatbot.
 * Handles user interactions and task management.
 */
public class Baymax {
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a Baymax instance.
     * @param filePath Path to the file where tasks are stored.
     */
    public Baymax(Path filePath) {
        Ui ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /**
     * Runs the chatbot, handling user input until "bye" is entered.
     */
    public void run() {
        Ui.reply("Hello! I'm Baymax", "How can I assist you?");
        String input = Ui.getInput();
        while (!input.startsWith("bye")) {
            Parser.parse(input, tasks, storage);
            input = Ui.getInput();
        }
        Ui.reply("Goodbye! *slowly deflates*");
    }

    /**
     * The main method to start the chatbot.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Baymax(FILE_PATH).run();
    }
}
