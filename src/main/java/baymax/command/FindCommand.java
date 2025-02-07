package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to find tasks that contain a specific keyword.
 */
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

    /**
     * Executes the command by searching for tasks that contain the keyword,
     * and updating the GUI with the search results.
     *
     * @param guiController The GUI controller to handle user interactions.
     * @param storage The storage handler (not used in this command).
     * @param tasks The task list to search for matching tasks.
     */
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        guiController.addUserDialog();
        String reply = "These are the tasks you are looking for:\n" + tasks.listTasks(findTask);
        guiController.addBaymaxDialog(reply, COMMAND_ID);

    }
}
