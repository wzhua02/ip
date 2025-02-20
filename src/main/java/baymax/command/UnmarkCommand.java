package baymax.command;

import baymax.exception.BaymaxException;
import baymax.gui.Gui;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to unmark a task in the task list.
 */
public class UnmarkCommand extends Command {
    public static final String COMMAND_ID = "unmark";
    private int targetIndex;

    /**
     * Contructor for UnmarkCommand object.
     *
     * @param taskIndex Index of task to be marked.
     */
    public UnmarkCommand(int taskIndex) {
        this.targetIndex = taskIndex;
    }

    /**
     * Executes the command by unmarking the specified task as completed,
     * saving the updated task list to storage, and updating the GUI with the response.
     *
     * @param gui The GUI to handle user interactions.
     * @param storage The storage handler to save task data.
     * @param tasks The task list containing the task to be marked.
     */
    @Override
    public void execute(Gui gui, Storage storage, TaskList tasks) throws BaymaxException {
        Task theTask = tasks.getTask(targetIndex);
        theTask.unmarkTask();
        tasks.save(storage);
        gui.addUserDialog();
        String reply = "Okie this is marked as not done yet:\n" + theTask;
        gui.addBaymaxDialog(reply, COMMAND_ID);
    }
}
