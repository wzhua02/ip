package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to sort the tasks in the task list.
 */
public class SortCommand extends Command {
    public static final String COMMAND_ID = "sort";
    /**
     * Executes the command by retrieving all tasks from the task list
     * and updating the GUI with the list of tasks.
     *
     * @param guiController The GUI controller to handle user interactions.
     * @param storage The storage handler (not used in this command).
     * @param tasks The task list to retrieve tasks from.
     */
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        guiController.addUserDialog();
        String reply = "Here are your tasks in sorted order:\n" + tasks.sortTasks();
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
