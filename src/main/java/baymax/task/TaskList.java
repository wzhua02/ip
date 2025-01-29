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

    /**
     * Adds a task to the list based on input and task type.
     *
     * @param input The raw user input specifying the task.
     * @param type  The type of task to add (todo, deadline, event).
     * @return The newly added task.
     * @throws BaymaxException If the input format is invalid.
     */
    public Task addTask(String input, String type) throws BaymaxException {
        Task newTask;
        int spaceIdx = input.indexOf(" ");
        if (spaceIdx < 0) {
            throw new BaymaxException("Let me know what task you wish to add.");
        }
        switch (type) {
        case "todo" -> {
            String taskDescribe = input.substring(spaceIdx + 1);
            newTask = new Todo(taskDescribe);
        }
        case "deadline" -> {
            int byIdx = input.indexOf("/by");
            if (byIdx < 0) {
                throw new BaymaxException("Let me know the deadline of the task.");
            }
            String taskDescribe = input.substring(spaceIdx + 1, byIdx - 1);
            String deadlineString = input.substring(byIdx + 4);
            newTask = new Deadline(taskDescribe, Parser.parseDateTime(deadlineString));
        }
        case "event" -> {
            int fromIdx = input.indexOf("/from");
            int toIdx = input.indexOf("/to");
            if (fromIdx < 0 || toIdx < 0) {
                throw new BaymaxException("Let me know when the event starts and ends.");
            }
            String taskDescribe = input.substring(spaceIdx + 1, fromIdx - 1);
            String fromDate = input.substring(fromIdx + 6, toIdx - 1);
            String toDate = input.substring(toIdx + 4);
            newTask = new Event(taskDescribe, Parser.parseDateTime(fromDate), Parser.parseDateTime(toDate));
        }
        default -> throw new BaymaxException("What type of task is this?");
        }
        taskList.add(newTask);
        return newTask;
    }

    /**
     * Removes a task from the list based on input.
     *
     * @param input The raw user input specifying which task to remove.
     * @return The removed task.
     * @throws BaymaxException If the task index is invalid.
     */
    public Task removeTask(String input) throws BaymaxException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new BaymaxException("Do let me know which task to remove.");
        }
        int idx = Integer.parseInt(parts[1]) - 1;
        if (idx < 0 || idx >= taskList.size()) {
            throw new BaymaxException("I do not know which task you are referring to.");
        }
        Task theTask = taskList.get(idx);
        taskList.remove(theTask);
        return theTask;
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
