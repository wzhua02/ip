package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        guiController.addUserDialog();
        String reply = "Goodbye! *slowly deflates*";
        guiController.addBaymaxDialog(reply);
    }


    @Override
    public boolean isBye() {
        return true;
    }

}
