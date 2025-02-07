package baymax.command;

import baymax.gui.GuiController;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to mark a task as completed in the task list.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_ID = "mark";
    private int targetIndex;

    /**
     * Contructor for MarkCommand object.
     *
     * @param taskIndex Index of task to be marked.
     */
    public MarkCommand(int taskIndex) {
        this.targetIndex = taskIndex;
    }

    /**
     * Executes the command by marking the specified task as completed,
     * saving the updated task list to storage, and updating the GUI with the response.
     *
     * @param guiController The GUI controller to handle user interactions.
     * @param storage The storage handler to save task data.
     * @param tasks The task list containing the task to be marked.
     */
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        Task theTask = tasks.getTask(targetIndex);
        theTask.markTask();
        tasks.save(storage);
        guiController.addUserDialog();
        String reply = "Okie dokie this is marked as done:\n" + theTask;
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
