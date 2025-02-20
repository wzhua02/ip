package baymax;

import java.nio.file.Path;
import java.nio.file.Paths;

import baymax.command.Command;
import baymax.command.ErrorCommand;
import baymax.exception.BaymaxException;
import baymax.gui.Gui;
import baymax.task.TaskList;
import baymax.util.Parser;
import baymax.util.Storage;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

/**
 * The main class for the Baymax chatbot.
 * Handles user interactions and task management.
 */
public class Baymax {
    private static final Path FILE_PATH = Paths.get("./", "data", "tasks.txt");

    private final Storage storage;
    private final TaskList tasks;
    private final Gui gui;

    /**
     * Constructs a Baymax instance.
     */
    public Baymax() {
        gui = Gui.getInstance();
        gui.setBaymax(this);
        storage = new Storage(FILE_PATH);
        tasks = new TaskList(storage.load());
    }

    /**
     * Processes the user input and returns the corresponding response.
     *
     * @param input The user input as a string.
     */
    public void getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(gui, storage, tasks);
            if (command.isBye()) {
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(event -> Platform.exit());
                delay.play();
            }
        } catch (BaymaxException e) {
            assert !e.getMessage().isBlank() : "Exception should have a message";
            ErrorCommand errorCommand = new ErrorCommand(e.getMessage());
            errorCommand.execute(gui, storage, tasks);
        } catch (IndexOutOfBoundsException e) {
            ErrorCommand errorCommand = new ErrorCommand(e.getMessage());
            errorCommand.execute(gui, storage, tasks);
        }
    }
}
