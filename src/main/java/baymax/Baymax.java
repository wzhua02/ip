package baymax;

import baymax.io.Storage;
import baymax.task.TaskList;
import baymax.util.Parser;

/**
 * The main class for the Baymax chatbot.
 * Handles user interactions and task management.
 */
public class Baymax {
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a Baymax instance.
     */
    public Baymax() {
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    public String getResponse(String input) {
        return Parser.parse(input, tasks, storage);
    }
}
