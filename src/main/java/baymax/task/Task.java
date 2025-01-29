package baymax.task;

/**
 * Represents an abstract task with a description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with a given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a status icon indicating whether the task is done.
     *
     * @return "X" if the task is completed, otherwise a space " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks or unmarks the task as completed.
     *
     * @param mark {@code true} to mark as done, {@code false} to unmark.
     */
    public void marker(boolean mark) {
        this.isDone = mark;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return The task description.
     */
    @Override
    public String toString() {
        return description;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts the task into a formatted string for saving.
     * This method must be implemented by subclasses to define
     * how the task should be stored.
     *
     * @return A formatted string representing the task for storage.
     */
    public abstract String toSaveFormat();
}
