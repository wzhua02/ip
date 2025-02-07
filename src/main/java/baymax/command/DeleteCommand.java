package baymax.command;

import baymax.gui.GuiController;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.util.Storage;

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
