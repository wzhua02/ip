package baymax.command;

import baymax.gui.Gui;
import baymax.task.Deadline;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to add a deadline task to the task list.
 */
public class AddDeadlineCommand extends Command {
    public static final String COMMAND_ID = "deadline";
    private Deadline newDeadline;

    /**
     * Constructor for AddDeadline object.
     *
     * @param deadline The Deadline task to be added.
     */
    public AddDeadlineCommand(Deadline deadline) {
        this.newDeadline = deadline;
    }

    /**
     * Executes the command by adding the deadline task to the task list,
     * saving the updated list to storage, and updating the GUI with the response.
     *
     * @param gui The GUI to handle user interactions.
     * @param storage The storage handler to save task data.
     * @param tasks The task list where the deadline will be added.
     */
    @Override
    public void execute(Gui gui, Storage storage, TaskList tasks) {
        tasks.addTask(newDeadline);
        tasks.save(storage);
        gui.addUserDialog();

        String reply = "Got it. Added this task:\n" + newDeadline + "\nNow you have "
                + tasks.getSize() + " tasks in the list.";
        gui.addBaymaxDialog(reply, COMMAND_ID);
    }
}
