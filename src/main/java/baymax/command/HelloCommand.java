package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to display a welcome message
 */
public class HelloCommand extends Command {
    public static final String COMMAND_ID = "hello";

    /**
     * Executes the command by displaying a welcome message and updating the GUI.
     *
     * @param guiController The GUI controller to handle user interactions.
     * @param storage The storage handler (not used in this command).
     * @param tasks The task list (not modified in this command).
     */
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        guiController.addUserDialog();
        String reply = "Hello! I'm Baymax\n" + "How can I assist you?";
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
