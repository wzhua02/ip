import java.util.Scanner;
import java.util.ArrayList;

public class Baymax {
    private static void reply(String msg, String... otherMsgs) {
        String indent = "    ";
        String horizontal_line = indent + "_".repeat(50);
        System.out.println(horizontal_line);
        System.out.println(indent + msg);
        for (String msgs : otherMsgs) {
            System.out.println(indent + msgs);
        }
        System.out.println(horizontal_line);
    }
    private static Task markTask(String input, ArrayList<Task> taskList, String cmd) throws BaymaxException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new BaymaxException("Do let me know which task to mark/unmark. ");
        }
        int idx = Integer.parseInt(parts[1]) - 1;
        if (idx < 0 || idx >= taskList.size()) {
            throw new BaymaxException("I do not know which task you are referring to. ");
        }
        Task theTask = taskList.get(idx);
        theTask.marker(cmd.equals("mark"));
        return theTask;
    }
    private static Task createTask(String input, String cmd) throws BaymaxException {
        Task newTask;
        int spaceIdx = input.indexOf(" ");
        if (spaceIdx < 0) {
            throw new BaymaxException("Do let me know what todo task you wish to add. ");
        }
        switch (cmd) {
            case "todo" -> {
                String taskDescribe = input.substring(spaceIdx + 1);
                newTask = new Todo(taskDescribe, cmd);
            }
            case "deadline" -> {
                int byIdx = input.indexOf("/by");
                String taskDescribe = input.substring(spaceIdx + 1, byIdx - 1);
                String deadlineDate = input.substring(byIdx + 4);
                newTask = new Deadline(taskDescribe, cmd, deadlineDate);
            }
            case "event" -> {
                int fromIdx = input.indexOf("/from");
                int toIdx = input.indexOf("/to");
                String taskDescribe = input.substring(spaceIdx + 1, fromIdx - 1);
                String fromDate = input.substring(fromIdx + 6, toIdx - 1);
                String toDate = input.substring(toIdx + 4);
                newTask = new Event(taskDescribe, cmd, fromDate, toDate);
            }
            default -> throw new BaymaxException("What type of task is this?");
        }
        return newTask;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

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
                        reply("Here are your tasks: ", allTaskArray.toArray(new String[0]));
                    }
                    case "mark", "unmark" -> {
                        Task theTask = markTask(input, taskList, cmd);
                        theTask.marker(cmd.equals("mark"));
                        String markMsg = cmd.equals("mark")
                                ? "Okie dokie this is marked as done: "
                                : "Okie this is marked as not done yet: ";
                        reply(markMsg, "   " + theTask);
                    }
                    case "todo", "deadline", "event" -> {
                        Task newTask = createTask(input, cmd);
                        taskList.add(newTask);
                        reply("Got it. Added this task: ",
                                newTask.toString(),
                                "Now you have " + taskList.size() + " tasks in the list. ");
                    }
                    default -> {
                        throw new BaymaxException("I do not understand what you are saying. ");
                    }
                }
            } catch (BaymaxException e) {
                reply(e.getMessage());
            } finally {
                input = scan.nextLine();
                cmd = input.split(" ")[0];
            }
        }
        reply("Bye. Hope to see you again soon!");
    }
}
