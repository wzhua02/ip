public class Deadline extends Task {
    protected String description;
    protected boolean isDone;
    protected String type;
    protected String deadlineDate;

    public Deadline(String description, String type, String deadlineDate) {
        super(description, type);
        this.type = "D";
        this.deadlineDate = deadlineDate;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        return typeStr + markStr + super.toString() + " (by: " + getDeadlineDate() + ")";
    }
}
