import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected LocalDateTime toDate;
    protected LocalDateTime fromDate;

    public Event(String description, String type, LocalDateTime fromDate, LocalDateTime toDate) {
        super(description, type);
        this.type = "E";
        this.toDate = toDate;
        this.fromDate = fromDate;
    }

    public Event(String description, String type, LocalDateTime fromDate, LocalDateTime toDate, boolean isDone) {
        super(description, type);
        this.type = "E";
        this.toDate = toDate;
        this.fromDate = fromDate;
        this.isDone = isDone;
    }

    public String getToDate() {
        return toDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    public String getFromDate() {
        return fromDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        String fromToStr = " (from: " + this.getFromDate() + ") (to: " + this.getToDate() + ")";
        return typeStr + markStr + super.toString() + fromToStr;
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + super.toString()
                + " | " + this.getFromDate() + " | " + this.getToDate();
    }
}
