import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Baymax {
    private static class Task {
        protected String description;
        protected boolean isDone;
        protected String type;
        protected String toDate;
        protected String fromDate;

        public Task(String description, String type) {
            this.description = description;
            this.isDone = false;
            this.type = "T";
        }

        public Task(String description, String type, String toDate) {
            this.description = description;
            this.isDone = false;
            this.type = "D";
            this.toDate = toDate;
        }

        public Task(String description, String type, String fromDate, String toDate) {
            this.description = description;
            this.isDone = false;
            this.type = "E";
            this.toDate = toDate;
            this.fromDate = fromDate;
        }

        public String getStatusIcon() {
            return (isDone ? "X" : " "); // mark done task with X
        }

        public void marker(boolean mark) {
            this.isDone = mark;
        }

        public String getToDate() {
            return toDate;
        }

        public String getFromDate() {
            return fromDate;
        }

        @Override
        public String toString() {
            String typeStr = "[" + this.type + "]";
            String markStr = "[" + this.getStatusIcon() + "] ";
            String retString = typeStr + markStr + description;
            if (this.type.equals("D")) {
                retString += " (by: " + toDate + ")";
            } else if (this.type.equals("E")) {
                retString += " (from: " + fromDate + " to: " + toDate + ")";
            }
            return retString;
        }
    }
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
        reply("Hello! I'm Baymax", "What can I do for you?");
        String input = scan.nextLine();
        String cmd = input.split(" ")[0];
        while (!cmd.equals("bye")) {
            if (cmd.equals("list")) {
                ArrayList<String> allTaskArray = new ArrayList<>();
                for (int i = 0; i < taskList.size(); i++) {
                    Task task = taskList.get(i);
                    allTaskArray.add((i + 1) + ". " + task);
                }
                reply("Here are your tasks: ", allTaskArray.toArray(new String[0]));
            } else if (cmd.equals("mark") || cmd.equals("unmark")) {
                int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                Task theTask = taskList.get(idx);
                theTask.marker(cmd.equals("mark"));
                String markMsg = cmd.equals("mark")
                        ? "Okie dokay this is marked as done: "
                        : "Okie this is marked as not done yet: ";
                reply(markMsg, "   " + theTask);
            } else if (cmd.equals("todo")) {
                int spaceIdx = input.indexOf(" ");
                String taskDescribe = input.substring(spaceIdx + 1);
                Task newTask = new Task(taskDescribe, cmd);
                taskList.add(newTask);
                reply("Got it. Added this task: ",
                        newTask.toString(),
                        "Now you have " + taskList.size() + " tasks in the list. ");
            } else if (cmd.equals("deadline")) {
                int spaceIdx = input.indexOf(" ");
                int byIdx = input.indexOf("/by");
                String taskDescribe = input.substring(spaceIdx + 1, byIdx - 1);
                String deadlineDate = input.substring(byIdx + 4);
                Task newTask = new Task(taskDescribe, cmd, deadlineDate);
                taskList.add(newTask);
                reply("Got it. Added this task: ",
                        newTask.toString(),
                        "Now you have " + taskList.size() + " tasks in the list. ");
            } else if (cmd.equals("event")) {
                int spaceIdx = input.indexOf(" ");
                int fromIdx = input.indexOf("/from");
                int toIdx = input.indexOf("/to");
                String taskDescribe = input.substring(spaceIdx + 1, fromIdx - 1);
                String fromDate = input.substring(fromIdx + 6, toIdx - 1);
                String toDate = input.substring(toIdx + 4);
                Task newTask = new Task(taskDescribe, cmd, fromDate, toDate);
                taskList.add(newTask);
                reply("Got it. Added this task: ",
                        newTask.toString(),
                        "Now you have " + taskList.size() + " tasks in the list. ");
            } else {
                taskList.add(new Task(input, null));
                reply("added " + input);
            }
            input = scan.nextLine();
            cmd = input.split(" ")[0];
        }
        reply("Bye. Hope to see you again soon!");
    }
}
