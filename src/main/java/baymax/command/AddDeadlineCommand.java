package baymax.command;

import baymax.gui.GuiController;
import baymax.task.Deadline;
import baymax.task.TaskList;
import baymax.util.Storage;

public class AddDeadlineCommand extends Command {
    private Deadline newDeadline;

    /**
     * Constructor for AddDeadlineCommand object.
     *
     * @param deadline The deadline task to be added.
     */
    public AddDeadlineCommand(Deadline deadline) {
        this.newDeadline = deadline;
    }

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        tasks.addTask(newDeadline);
        tasks.save(storage);
        guiController.addUserDialog();
        String reply = "Got it. Added this task:\n" + newDeadline + "\nNow you have " + tasks.size()
                + " tasks in the list.";
        guiController.addBaymaxDialog(reply);
    }
}
