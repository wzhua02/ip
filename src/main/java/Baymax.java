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
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        //Chatbot function
        reply("Hello! I'm Baymax", "How can I assist you?");
        String input = scan.nextLine();
        String cmd = input.split(" ")[0];
        while (!cmd.equals("bye")) {
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
                    int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task theTask = taskList.get(idx);
                    theTask.marker(cmd.equals("mark"));
                    String markMsg = cmd.equals("mark")
                            ? "Okie dokie this is marked as done: "
                            : "Okie this is marked as not done yet: ";
                    reply(markMsg, "   " + theTask);
                }
                case "todo" -> {
                    int spaceIdx = input.indexOf(" ");
                    String taskDescribe = input.substring(spaceIdx + 1);
                    Task newTask = new Todo(taskDescribe, cmd);
                    taskList.add(newTask);
                    reply("Got it. Added this task: ",
                            newTask.toString(),
                            "Now you have " + taskList.size() + " tasks in the list. ");
                }
                case "deadline" -> {
                    int spaceIdx = input.indexOf(" ");
                    int byIdx = input.indexOf("/by");
                    String taskDescribe = input.substring(spaceIdx + 1, byIdx - 1);
                    String deadlineDate = input.substring(byIdx + 4);
                    Task newTask = new Deadline(taskDescribe, cmd, deadlineDate);
                    taskList.add(newTask);
                    reply("Got it. Added this task: ",
                            newTask.toString(),
                            "Now you have " + taskList.size() + " tasks in the list. ");
                }
                case "event" -> {
                    int spaceIdx = input.indexOf(" ");
                    int fromIdx = input.indexOf("/from");
                    int toIdx = input.indexOf("/to");
                    String taskDescribe = input.substring(spaceIdx + 1, fromIdx - 1);
                    String fromDate = input.substring(fromIdx + 6, toIdx - 1);
                    String toDate = input.substring(toIdx + 4);
                    Task newTask = new Event(taskDescribe, cmd, fromDate, toDate);
                    taskList.add(newTask);
                    reply("Got it. Added this task: ",
                            newTask.toString(),
                            "Now you have " + taskList.size() + " tasks in the list. ");
                }
                default -> {
                    reply("I do not understand what you're saying. " + input);
                }
            }
            input = scan.nextLine();
            cmd = input.split(" ")[0];
        }
        reply("Bye. Hope to see you again soon!");
    }
}
