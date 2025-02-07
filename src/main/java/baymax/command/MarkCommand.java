package baymax.command;

import baymax.gui.GuiController;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.util.Storage;

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

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        Task theTask = tasks.getTask(targetIndex);
        theTask.marker(true);
        tasks.save(storage);
        guiController.addUserDialog();
        String reply = "Okie dokie this is marked as done:\n" + theTask;
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
