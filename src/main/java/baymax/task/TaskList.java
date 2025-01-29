/**
 * Represents a list of tasks and provides operations to manage them.
 */
package baymax.task;

import java.util.ArrayList;

import baymax.exception.BaymaxException;
import baymax.io.Storage;
import baymax.util.Parser;

/**
 * Manages a list of tasks, allowing addition, removal, retrieval, and persistence.
 */
public class TaskList {
    private final ArrayList<Task> taskList;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        taskList = new ArrayList<>();
    }

    /**
     * Constructs a TaskList from a list of task strings.
     *
     * @param taskListString The list of task strings retrieved from storage.
     */
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

    /**
     * Returns a list of task descriptions formatted as strings.
     *
     * @return A list of task descriptions.
     */
    public ArrayList<String> toStringList() {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            returnList.add((i + 1) + ". " + task);
        }
        return returnList;
    }

    /**
     * Retrieves a task by its index.
     *
     * @param idx The index of the task.
     * @return The task at the specified index.
     */
    public Task getTask(int idx) {
        return taskList.get(idx);
    }

    public int getTaskIdx(Task task) {
        return taskList.indexOf(task) + 1;
    }

    /**
     * Adds a task to the list based on input and task type.
     *
     * @param newTask The task to add to the list.
     * @return The newly added task.
     * @throws BaymaxException If the input format is invalid.
     */
    public void addTask(Task newTask) throws BaymaxException {
        taskList.add(newTask);
    }
    /**
     * Removes a task from the list based on input.
     *
     * @param theTask The task to remove from the list.
     * @throws BaymaxException If the task index is invalid.
     */
    public void removeTask(Task theTask) throws BaymaxException {
        taskList.remove(theTask);
    }

    public ArrayList<Task> findTask(String subString) throws BaymaxException {
        ArrayList<Task> foundTasks = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getDescription().toLowerCase().contains(subString)) {
                foundTasks.add(task);
            }
        }

        if (foundTasks.isEmpty()) {
            throw new BaymaxException("No tasks found containing: " + subString);
        } else {
            return foundTasks;
        }
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return taskList.size();
    }

    /**
     * Saves the task list to storage.
     *
     * @param storage The storage system to save tasks to.
     */
    public void save(Storage storage) {
        ArrayList<String> saveList = new ArrayList<>();
        for (Task task : taskList) {
            saveList.add(task.toSaveFormat());
        }
        storage.save(saveList);
    }
}
