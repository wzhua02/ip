import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Baymax {
    private static class Task {
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
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
                    allTaskArray.add((i + 1) + ". [" + task.getStatusIcon() + "] " + task);
                }
                reply("Here are your tasks: ", allTaskArray.toArray(new String[0]));
            } else if (cmd.equals("mark")) {
                int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                Task theTask = taskList.get(idx);
                theTask.marker(true);
                reply("Okie this is marked as done: ", "   [" + theTask.getStatusIcon() + " ] " + theTask);
            } else if (cmd.equals("unmark")) {
                int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                Task theTask = taskList.get(idx);
                theTask.marker(false);
                reply("Okie this is marked as not done yet: ", "   [" + theTask.getStatusIcon() + "] " + theTask);
            } else {
                taskList.add(new Task(input));
                reply("added " + input);
            }
            input = scan.nextLine();
            cmd = input.split(" ")[0];
        }
        reply("Bye. Hope to see you again soon!");
    }
}
