import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList () {
        taskList = new ArrayList<>();
    }

    public TaskList (ArrayList<String> taskListString) {
        taskList = new ArrayList<>();
        try {
            for (String line : taskListString) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                switch (type) {
                case "T" -> taskList.add(new Todo(parts[2], isDone));
                case "D" -> taskList.add(new Deadline(parts[2], Parser.parseDateTime(parts[3]), isDone));
                case "E" -> taskList.add(new Event(parts[2], Parser.parseDateTime(parts[3]), Parser.parseDateTime(parts[4]),
                        isDone));
                }
            }
        } catch (BaymaxException e) {
            System.err.println("Date format Error: " + e.getMessage());
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

    public Task removeTask(String input) throws BaymaxException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new BaymaxException("Do let me know which task to mark/unmark.");
        }
        int idx = Integer.parseInt(parts[1]) - 1;
        if (idx < 0 || idx >= taskList.size()) {
            throw new BaymaxException("I do not know which task you are referring to.");
        }
        Task theTask = taskList.get(idx);
        taskList.remove(theTask);
        return theTask;
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
