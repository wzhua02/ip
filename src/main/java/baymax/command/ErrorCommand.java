package baymax.command;


import baymax.gui.Gui;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to show an error message
 */
public class ErrorCommand extends Command {
    public static final String COMMAND_ID = "error";
    private String errorMessage;

    /**
     * Constructor for ErrorCommand.
     *
     * @param errorMessage error message to be shown.
     */
    public ErrorCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Displays an error message to the user
     *
     * @param gui The GUI to handle user interactions.
     * @param storage The storage handler to save task data.
     * @param tasks The task list from which the task will be deleted.
     */
    @Override
    public void execute(Gui gui, Storage storage, TaskList tasks) {
        gui.addUserDialog();
        String reply = errorMessage;
        gui.addBaymaxDialog(reply, COMMAND_ID);
    }
}
