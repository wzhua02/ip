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
        String[] input = scan.nextLine().split(" ");
        while (!input[0].equals("bye")) {
            switch(input[0]) {
                case "list":
                    ArrayList<String> allTaskArray = new ArrayList<>();
                    for (int i = 0; i < taskList.size(); i++) {
                        Task task = taskList.get(i);
                        allTaskArray.add((i + 1) + ". [" + task.getStatusIcon() + "] " + task);
                    }
                    reply("Here are your tasks: ", allTaskArray.toArray(new String[0]));
                case "mark":

                case "unmark":

                default:
                    taskList.add(new Task(input[0]));
                    reply("added " + input[0]);
            }
            input = scan.nextLine().split(" ");
        }
        reply("Bye. Hope to see you again soon!");
    }
}
