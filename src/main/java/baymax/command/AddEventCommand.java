package baymax.command;

import baymax.gui.Gui;
import baymax.task.Event;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents a command to add an event task to the task list.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_ID = "event";
    private Event newEvent;

    /**
     * Constructor for AddEventCommand object.
     *
     * @param event The event to be added to the task list.
     */
    public AddEventCommand(Event event) {
        this.newEvent = event;
    }

    /**
     * Executes the command by adding the event task to the task list,
     * saving the updated list to storage, and updating the GUI with the response.
     *
     * @param gui The GUI to handle user interactions.
     * @param storage The storage handler to save task data.
     * @param tasks The task list where the deadline will be added.
     */
    @Override
    public void execute(Gui gui, Storage storage, TaskList tasks) {
        tasks.addTask(newEvent);
        tasks.save(storage);
        gui.addUserDialog();
        String reply = "Got it. Added this task:\n" + newEvent + "\nNow you have " + tasks.getSize()
                + " tasks in the list.";
        gui.addBaymaxDialog(reply, COMMAND_ID);
    }
}
