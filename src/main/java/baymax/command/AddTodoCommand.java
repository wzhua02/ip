package baymax.command;

import baymax.gui.GuiController;
import baymax.task.TaskList;
import baymax.task.Todo;
import baymax.util.Storage;

/**
 * Represents a command to add a Todo task to the task list.
 */
public class AddTodoCommand extends Command {
    public static final String COMMAND_ID = "todo";
    private Todo newTodo;

    /**
     * Constructor for AddTodoCommand object.
     *
     * @param todo The todo task to be added.
     */
    public AddTodoCommand(Todo todo) {
        this.newTodo = todo;
    }

    /**
     * Executes the command by adding the Todo task to the task list,
     * saving the updated list to storage, and updating the GUI with the response.
     *
     * @param guiController The GUI controller to handle user interactions.
     * @param storage The storage handler to save task data.
     * @param tasks The task list where the deadline will be added.
     */
    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        tasks.addTask(newTodo);
        tasks.save(storage);
        guiController.addUserDialog();
        String reply = "Got it. Added this task:\n" + newTodo + "\nNow you have " + tasks.getSize()
                + " tasks in the list.";
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
