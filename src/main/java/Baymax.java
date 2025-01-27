import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class Baymax {
    private static final String INDENT = "    ";
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");

    public static LocalDateTime parseDateTime(String dateTimeStr) throws BaymaxException{
        String[] patterns = {
            "yyyy-MM-dd HH:mm",     //e.g. 2025-01-27 12:30
            "dd/MM/yyyy HH:mm",     //e.g. 27/01/2025 12:30
            "yyyy MM dd HH:mm",     //e.g. 2025 01 27 12:30
        };
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException e) {
                // Ignore and try the next pattern
            }
        }
        throw new BaymaxException("No valid date-time pattern found for: " + dateTimeStr
                + "\n" + INDENT + "Try to format the date-time in the following pattern:"
                + "\n" + INDENT + "e.g. 2025-01-27 12:30"
                + "\n" + INDENT + INDENT + " 27/01/2025 12:30"
                + "\n" + INDENT + INDENT + " 2025 01 27 12:30");
    }

    private static void reply(String msg, String... otherMsgs) {
        String horizontal_line = "_".repeat(50);
        System.out.println(horizontal_line);
        System.out.println(msg);
        for (String msgs : otherMsgs) {
            System.out.println(msgs);
        }
        System.out.println(horizontal_line);
    }
    private static Task getTask(String input, ArrayList<Task> taskList, String cmd) throws BaymaxException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new BaymaxException("Do let me know which task to mark/unmark.");
        }
        int idx = Integer.parseInt(parts[1]) - 1;
        if (idx < 0 || idx >= taskList.size()) {
            throw new BaymaxException("I do not know which task you are referring to.");
        }
        Task theTask = taskList.get(idx);
        theTask.marker(cmd.equals("mark"));
        return theTask;
    }
    private static Task createTask(String input, String cmd) throws BaymaxException {
        Task newTask;
        int spaceIdx = input.indexOf(" ");
        if (spaceIdx < 0) {
            throw new BaymaxException("Let me know what task you wish to add.");
        }
        switch (cmd) {
            case "todo" -> {
                String taskDescribe = input.substring(spaceIdx + 1);
                newTask = new Todo(taskDescribe, cmd);
            }
            case "deadline" -> {
                int byIdx = input.indexOf("/by");
                if (byIdx < 0) {
                    throw new BaymaxException("Let me know the deadline of the task.");
                }
                String taskDescribe = input.substring(spaceIdx + 1, byIdx - 1);
                String deadlineString = input.substring(byIdx + 4);
                newTask = new Deadline(taskDescribe, cmd, parseDateTime(deadlineString));
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
                newTask = new Event(taskDescribe, cmd, parseDateTime(fromDate), parseDateTime(toDate));
            }
            default -> throw new BaymaxException("What type of task is this?");
        }
        return newTask;
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        File dataFile = null;
        try {
            dataFile = new File(FILE_PATH.toString());
            if (!dataFile.exists()) {
                File dataDir = new File(FILE_PATH.getParent().toString());
                dataDir.mkdirs();
                dataFile.createNewFile();
                System.out.println("\"tasks.txt\" not found in directory. Created a new one.");
                return taskList;
            }
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                switch (type) {
                case "T" -> taskList.add(new Todo(parts[2], type, isDone));
                case "D" -> taskList.add(new Deadline(parts[2], type, parseDateTime(parts[3]), isDone));
                case "E" -> taskList.add(new Event(parts[2], type, parseDateTime(parts[3]), parseDateTime(parts[4]),
                        isDone));
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not Found Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Create File Error: " + e.getMessage());
        } catch (BaymaxException e) {
            System.out.println("Date format Error: " + e.getMessage());
        }
        return taskList;
    }

    private static void saveTasks(ArrayList<Task> taskList) {
        try {
            File dataFile = new File(FILE_PATH.toString());
            PrintWriter writer = new PrintWriter(new FileWriter(dataFile));
            for (Task task : taskList) {
                writer.println(task.toSaveFormat());
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to save tasks to file.");
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> taskList = loadTasks();

        System.setOut(new PrintStream(System.out) {
            @Override
            public void println(String x) {
                super.println(INDENT + x); // Add indentation before printing
            }

            @Override
            public void println(Object x) {
                super.println(INDENT + x); // Add indentation for Object toString()
            }
        });

        //Chatbot function
        reply("Hello! I'm Baymax", "How can I assist you?");
        String input = scan.nextLine();
        String cmd = input.split(" ")[0];

        while (!input.startsWith("bye")) {
            try {
                switch (cmd) {
                case "list" -> {
                    ArrayList<String> allTaskArray = new ArrayList<>();
                    for (int i = 0; i < taskList.size(); i++) {
                        Task task = taskList.get(i);
                        allTaskArray.add((i + 1) + ". " + task);
                    }
                    reply("Here are your tasks:", allTaskArray.toArray(new String[0]));
                }
                case "mark", "unmark" -> {
                    Task theTask = getTask(input, taskList, cmd);
                    theTask.marker(cmd.equals("mark"));
                    saveTasks(taskList);
                    String markMsg = cmd.equals("mark")
                            ? "Okie dokie this is marked as done:"
                            : "Okie this is marked as not done yet:";
                    reply(markMsg, "   " + theTask);
                }
                case "todo", "deadline", "event" -> {
                    Task newTask = createTask(input, cmd);
                    taskList.add(newTask);
                    saveTasks(taskList);
                    reply("Got it. Added this task:",
                            newTask.toString(),
                            "Now you have " + taskList.size() + " tasks in the list.");
                }
                case "delete" -> {
                    Task theTask = getTask(input, taskList, cmd);
                    taskList.remove(theTask);
                    saveTasks(taskList);
                    reply("Task removed!",
                            "   " + theTask,
                            "Now you have " + taskList.size() + " tasks in the list.");
                }
                default -> {
                    throw new BaymaxException("I cannot comprehend what you are saying.");
                }
                }
            } catch (BaymaxException e) {
                reply(e.getMessage());
            } finally {
                input = scan.nextLine();
                cmd = input.split(" ")[0];
            }
        }
        reply("Goodbye! *slowly deflates*");

    }
}
