import java.util.Arrays;
import java.util.Scanner;

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
        String[] taskList = new String[100];
        int taskCount = 0;

        //Chatbot function
        reply("Hello! I'm Baymax", "What can I do for you?");
        String input = scan.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                String[] allTasks = Arrays.copyOf(taskList, taskCount);
                reply("Here are your tasks: ", allTasks);
            } else {
                taskList[taskCount] = (taskCount + 1) + ". " + input;
                taskCount++;
                reply("added " + input);
            }
            input = scan.nextLine();
        }
        reply("Bye. Hope to see you again soon!");
    }
}
