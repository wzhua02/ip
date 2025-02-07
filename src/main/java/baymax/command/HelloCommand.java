package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

public class HelloCommand extends Command {
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        guiController.addUserDialog();
        String reply = "Hello! I'm Baymax\n" + "How can I assist you?";
        guiController.addBaymaxDialog(reply);
    }
}
