/**
 * Represents a Todo task.
 */
package baymax.task;

/**
 * Represents a Todo task, which is a basic task with a description and completion status.
 */
public class Todo extends Task {
    protected String description;
    protected boolean isDone;
    protected String type;

    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
        this.type = "T";
    }

    /**
     * Constructs a new Todo task with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      Whether the task is completed or not.
     */
    public Todo(String description, boolean isDone) {
        super(description);
        this.type = "T";
        this.isDone = isDone;
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return A formatted string representing the Todo task.
     */
    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        return typeStr + markStr + super.toString();
    }

    /**
     * Returns a formatted string representation of the Todo task for saving.
     *
     * @return A formatted string representing the Todo task for storage.
     */
    @Override
    public String toSaveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + super.toString();
    }
}
