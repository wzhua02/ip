package baymax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected LocalDateTime deadlineDate;

    public Deadline(String description, LocalDateTime deadlineDate) {
        super(description);
        this.type = "D";
        this.deadlineDate = deadlineDate;
    }

    public Deadline(String description, LocalDateTime deadlineDate, boolean isDone) {
        super(description);
        this.type = "D";
        this.deadlineDate = deadlineDate;
        this.isDone = isDone;
    }

    public String getDeadlineDate() {
        return deadlineDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        return typeStr + markStr + super.toString() + " (by: " + getDeadlineDate() + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + super.toString() + " | "
                + this.deadlineDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
