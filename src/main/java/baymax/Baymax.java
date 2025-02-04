package baymax;

import baymax.task.TaskList;
import baymax.util.Parser;
import baymax.util.Storage;

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

    /**
     * Processes the user input and returns the corresponding response.
     *
     * @param input The user input as a string.
     * @return A String array, where the first value is the command type the second value is the response generated
     *      after parsing the input.
     */
    public String[] getResponse(String input) {
        return Parser.parse(input, tasks, storage);
    }
}
