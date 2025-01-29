package baymax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a specific due date and time.
 */
public class Deadline extends Task {
    protected String type;
    protected LocalDateTime deadlineDate;

    /**
     * Constructs a Deadline task with a description and due date.
     * The task is initially marked as not done.
     *
     * @param description  The description of the deadline task.
     * @param deadlineDate The due date and time of the deadline task.
     */
    public Deadline(String description, LocalDateTime deadlineDate) {
        super(description);
        this.type = "D";
        this.deadlineDate = deadlineDate;
    }

    /**
     * Constructs a Deadline task with a description, due date, and completion status.
     *
     * @param description  The description of the deadline task.
     * @param deadlineDate The due date and time of the deadline task.
     * @param isDone       The completion status of the task.
     */
    public Deadline(String description, LocalDateTime deadlineDate, boolean isDone) {
        super(description);
        this.type = "D";
        this.deadlineDate = deadlineDate;
        this.isDone = isDone;
    }

    /**
     * Returns the formatted due date of the deadline task.
     *
     * @return The deadline date/time in "dd MMM yyyy HH:mm" format.
     */
    public String getDeadlineDate() {
        return deadlineDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        return typeStr + markStr + super.toString() + " (by: " + getDeadlineDate() + ")";
    }

    /**
     * Converts the deadline task into a formatted string for saving.
     *
     * @return A formatted string representing the deadline task for storage.
     */
    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + super.toString() + " | "
                + this.deadlineDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
