public class Todo extends Task {
    protected String description;
    protected boolean isDone;
    protected String type;

    public Todo(String description, String type) {
        super(description, type);
        this.type = "T";
    }

    public Todo(String description, String type, boolean isDone) {
        super(description, type);
        this.type = "T";
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        String typeStr = "[" + this.type + "]";
        String markStr = "[" + super.getStatusIcon() + "] ";
        return typeStr + markStr + super.toString();
    }

    @Override
    public String toSaveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + super.toString();
    }
}
