package baymax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start date/time and an end date/time.
 */
public class Event extends Task {
    protected String type;
    protected LocalDateTime toDate;
    protected LocalDateTime fromDate;

    /**
     * Constructs an Event task with a description, start date, and end date.
     * The task is initially marked as not done.
     *
     * @param description The description of the event.
     * @param fromDate    The start date/time of the event.
     * @param toDate      The end date/time of the event.
     */
    public Event(String description, LocalDateTime fromDate, LocalDateTime toDate) {
        super(description);
        this.type = "E";
        this.toDate = toDate;
        this.fromDate = fromDate;
    }

    /**
     * Constructs an Event task with a description, start date, end date, and completion status.
     *
     * @param description The description of the event.
     * @param fromDate    The start date/time of the event.
     * @param toDate      The end date/time of the event.
     * @param isDone      The completion status of the event.
     */
    public Event(String description, LocalDateTime fromDate, LocalDateTime toDate, boolean isDone) {
        super(description);
        this.type = "E";
        this.toDate = toDate;
        this.fromDate = fromDate;
        this.isDone = isDone;
    }

    /**
     * Returns the formatted end date of the event.
     *
     * @return The end date/time in "dd MMM yyyy HH:mm" format.
     */
    public String getToDate() {
        return toDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    /**
     * Returns the formatted start date of the event.
     *
     * @return The start date/time in "dd MMM yyyy HH:mm" format.
     */
    public String getFromDate() {
        return fromDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    /**
     * Returns the string representation of the event task.
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + getStatusIcon() + "] ";
        String fromToStr = " (from: " + getFromDate() + ") (to: " + getToDate() + ")";
        return typeStr + markStr + getDescription() + fromToStr;
    }

    /**
     * Converts the event task into a formatted string for saving.
     *
     * @return A formatted string representing the event for storage.
     */
    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + getDescription()
                + " | " + this.fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                + " | " + this.toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
