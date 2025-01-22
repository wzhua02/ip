public class Event extends Task {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected String toDate;
    protected String fromDate;

    public Event(String description, String type, String fromDate, String toDate) {
        super(description, type);
        this.type = "E";
        this.toDate = toDate;
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getFromDate() { return fromDate; }

    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        String fromToStr = " (from: " + this.getFromDate() + " to: " + this.getToDate() + ")";
        return typeStr + markStr + super.toString() + fromToStr;
    }
}
