package baymax.task;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import baymax.exception.BaymaxException;
import baymax.io.Storage;
import baymax.io.Ui;
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
     * Each task string is parsed and converted into the appropriate task type.
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
                default -> throw new BaymaxException("Task type not found.");
                }
            }
        } catch (BaymaxException e) {
            System.err.println(e.getMessage());
        }
    }

    public String listTasks() {
        if (taskList.isEmpty()) {
            return "No tasks found!";
        }
        return "Tasks:\n" + IntStream.range(0, taskList.size())
                .mapToObj(i -> Ui.INDENT + (i + 1) + ". " + taskList.get(i))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Retrieves a task by its index.
     *
     * @param idx The index of the task (0-based).
     * @return The task at the specified index.
     */
    public Task getTask(int idx) {
        return taskList.get(idx);
    }

    /**
     * Retrieves the 1-based index of a given task in the task list.
     *
     * @param task The task to find.
     * @return The 1-based index of the task, or 0 if not found.
     */
    public int getTaskIdx(Task task) {
        return taskList.indexOf(task) + 1;
    }

    /**
     * Adds a task to the list.
     *
     * @param newTask The task to add to the list.
     * @throws BaymaxException If there is an issue with adding the task.
     */
    public void addTask(Task newTask) throws BaymaxException {
        taskList.add(newTask);
    }

    /**
     * Removes a task from the list.
     *
     * @param theTask The task to remove from the list.
     * @throws BaymaxException If the task is not found.
     */
    public void removeTask(Task theTask) throws BaymaxException {
        taskList.remove(theTask);
    }

    /**
     * Finds tasks that contain the specified substring in their descriptions.
     *
     * @param subString The substring to search for.
     * @return A list of tasks that contain the given substring.
     * @throws BaymaxException If no tasks are found containing the substring.
     */
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
     * Saves the current task list to the specified storage.
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
