public class Todo extends Task {
    protected String description;
    protected boolean isDone;
    protected String type;

    public Todo(String description, String type) {
        super(description, type);
        this.type = "T";
    }

    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        return typeStr + markStr + super.toString();
    }
}
