package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

public class ListCommand extends Command {
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        String replyLine = tasks.listTasks();
        guiController.addUserDialog();
        String reply = "Here are your tasks:\n" + replyLine;
        guiController.addBaymaxDialog(reply);
    }
}
