package baymax.command;

import baymax.gui.Gui;
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
     * @param gui The GUI to handle user interactions.
     * @param storage The storage handler (not used in this command).
     * @param tasks The task list (not modified in this command).
     */
    @Override
    public void execute(Gui gui, Storage storage, TaskList tasks) {
        gui.addUserDialog();

        String reply = "Goodbye! *slowly deflates*";
        gui.addBaymaxDialog(reply, COMMAND_ID);
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
