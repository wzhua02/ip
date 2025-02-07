package baymax.command;

import baymax.gui.GuiController;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_ID = "delete";
    private int targetIndex;

    /**
     * Constructor for DeleteCommand.
     *
     * @param taskIndex Index of the task to be deleted.
     */
    public DeleteCommand(int taskIndex) {
        this.targetIndex = taskIndex;
    }

    /**
     * Executes the command by removing the specified task from the task list,
     * saving the updated list to storage, and updating the GUI with the response.
     *
     * @param guiController The GUI controller to handle user interactions.
     * @param storage The storage handler to save task data.
     * @param tasks The task list from which the task will be deleted.
     */
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        Task theTask = tasks.getTask(targetIndex);
        tasks.removeTask(theTask);
        tasks.save(storage);
        guiController.addUserDialog();
        String reply = "Task removed!\n" + "   " + theTask + "\nNow you have " + tasks.size()
                + " tasks in the list.";
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
