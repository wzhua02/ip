package baymax.task;

import java.util.ArrayList;

import baymax.exception.BaymaxException;
import baymax.io.Storage;
import baymax.util.Parser;

public class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public TaskList(ArrayList<String> taskListString) {
        taskList = new ArrayList<>();
        try {
            for (String line : taskListString) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                switch (type) {
                case "T" -> taskList.add(new Todo(parts[2], isDone));
                case "D" -> taskList.add(new Deadline(parts[2], Parser.parseDateTime(parts[3]), isDone));
                case "E" -> taskList.add(new Event(parts[2], Parser.parseDateTime(parts[3]),
                        Parser.parseDateTime(parts[4]), isDone));
                default -> throw new BaymaxException("Task type not found. ");
                }
            }
        } catch (BaymaxException e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<String> toStringList() {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            returnList.add((i + 1) + ". " + task);
        }
        return returnList;
    }

    public Task getTask(int idx) {
        return taskList.get(idx);
    }

    public void addTask(Task newTask) throws BaymaxException {
        taskList.add(newTask);
    }

    public void removeTask(Task theTask) throws BaymaxException {
        taskList.remove(theTask);
    }

    public int size() {
        return taskList.size();
    }

    public void save(Storage storage) {
        ArrayList<String> saveList = new ArrayList<>();
        for (Task task : taskList) {
            saveList.add(task.toSaveFormat());
        }
        storage.save(saveList);
    }
}
