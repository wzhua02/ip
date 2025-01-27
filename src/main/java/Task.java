abstract class Task {
    protected String description;
    protected boolean isDone;
    protected String type;

    public Task(String description, String type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void marker(boolean mark) {
        this.isDone = mark;
    }

    @Override
    public String toString() {
        return description;
    }

    public abstract String toSaveFormat();
}
