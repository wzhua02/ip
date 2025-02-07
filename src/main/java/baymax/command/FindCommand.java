package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

public class FindCommand extends Command {
    public static final String COMMAND_ID = "find";
    private String findTask;

    /**
     * Constructor for FindCommand.
     *
     * @param searchWord Word to search for in tasks.
     */
    public FindCommand(String searchWord) {
        this.findTask = searchWord;
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        guiController.addUserDialog();
        String reply = "These are the tasks you are looking for:\n" + tasks.listTasks(findTask);
        guiController.addBaymaxDialog(reply, COMMAND_ID);

    }
}
