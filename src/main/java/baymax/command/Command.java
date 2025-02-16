package baymax.command;

import baymax.gui.Gui;
import baymax.task.TaskList;
import baymax.util.Storage;

/**
 * Represents an action for interacting with ui, storage and task list.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param gui the ui for text display
     * @param storage the storage for saving and loading tasks
     * @param tasks the task list to be modified
     */
    public abstract void execute(Gui gui, Storage storage, TaskList tasks);

    /**
     * Returns whether the command is an exit command.
     *
     * @return a boolean indicating if the command is an exit command
     */
    public boolean isBye() {
        return false;
    }
}
