package baymax.command;

import baymax.gui.GuiController;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.util.Storage;

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

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        Task theTask = tasks.getTask(targetIndex);
        theTask.marker(false);
        tasks.save(storage);
        guiController.addUserDialog();
        String reply = "Okie this is marked as not done yet:\n" + theTask;
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
