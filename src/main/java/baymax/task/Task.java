package baymax.task;

/**
 * Represents an abstract task with a description and completion status.
 */
public abstract class Task implements Comparable<Task> {
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
     * Marks the task as completed.
     */
    public void markTask() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkTask() {
        this.isDone = false;
    }

    /**
     * Compares this task with another task for ordering.
     * Tasks are first compared based on completion status (unfinished tasks first).
     * If both tasks have the same completion status, they are sorted alphabetically by description.
     *
     * @param other The other task to compare to.
     * @return A negative integer, zero, or a positive integer as this task
     *         is less than, equal to, or greater than the specified task.
     */
    @Override
    public int compareTo(Task other) {
        // Determine type priority: Task (0), Deadline (1), Event (2)
        int thisType = this instanceof Event ? 2 : (this instanceof Deadline ? 1 : 0);
        int otherType = other instanceof Event ? 2 : (other instanceof Deadline ? 1 : 0);

        // Sort by type priority (Tasks < Deadlines < Events)
        if (thisType != otherType) {
            return Integer.compare(thisType, otherType);
        }

        // If both are Deadline tasks, sort by deadlineDate
        if (this instanceof Deadline && other instanceof Deadline) {
            Deadline thisDeadline = (Deadline) this;
            Deadline otherDeadline = (Deadline) other;
            return thisDeadline.deadlineDate.compareTo(otherDeadline.deadlineDate);
        }

        // If both are Event tasks, sort by fromDate
        if (this instanceof Event && other instanceof Event) {
            Event thisEvent = (Event) this;
            Event otherEvent = (Event) other;
            return thisEvent.fromDate.compareTo(otherEvent.fromDate);
        }

        // Default sorting: isDone first, then alphabetically by description
        if (this.isDone != other.isDone) {
            return Boolean.compare(this.isDone, other.isDone);
        }
        return this.description.compareToIgnoreCase(other.description);
    }

    /**
     * Returns the string representation of the task.
     *
     * @return The task description.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Converts the task into a formatted string for saving.
     * This method must be implemented by subclasses to define
     * how the task should be stored.
     *
     * @return A formatted string representing the task for storage.
     */
    public String toSaveFormat() {
        return (isDone ? "1" : "0") + " | " + this.description;
    }
}
