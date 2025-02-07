package baymax.command;

import baymax.gui.GuiController;
import baymax.task.Task;
import baymax.task.TaskList;
import baymax.task.Todo;
import baymax.util.Storage;

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

    @Override
    public void execute(GuiController guiController, Storage storage, TaskList tasks) {
        tasks.addTask(newTodo);
        tasks.save(storage);
        guiController.addUserDialog();
        String reply = "Got it. Added this task:\n" + newTodo + "\nNow you have " + tasks.size()
                + " tasks in the list.";
        guiController.addBaymaxDialog(reply, COMMAND_ID);
    }
}
