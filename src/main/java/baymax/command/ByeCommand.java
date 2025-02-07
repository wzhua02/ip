package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {
    public static final String COMMAND_ID = "bye";

    /**
     * Executes the command by displaying a farewell message and updating the GUI.
     *
     * @param guiController The GUI controller to handle user interactions.
     * @param storage The storage handler (not used in this command).
     * @param tasks The task list (not modified in this command).
     */
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        guiController.addUserDialog();

        String reply = "Goodbye! *slowly deflates*";
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }

    /**
     * Indicates that this command is an exit command.
     *
     * @return {@code true}, as this command signifies exiting the program.
     */
    @Override
    public boolean isBye() {
        return true;
    }
}
