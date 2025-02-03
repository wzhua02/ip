package baymax.io;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a user interface (UI) for interacting with the user via standard input and output.
 * This class is responsible for reading user input and formatting output with indentation.
 */
public class Ui {
    public static final String INDENT = "    ";
    private static final Scanner scan = new Scanner(System.in);

    /**
     * Initializes the UI by overriding the default system output behavior.
     * This method modifies {@code System.out} to prepend an indentation
     * before every printed line.
     */
    public static void initializeUi() {
        System.setOut(new PrintStream(System.out) {
            /**
             * Overrides the println method to add indentation before printing the message.
             *
             * @param x the message to print
             */
            @Override
            public void println(String x) {
                super.println(INDENT + x); // Add indentation before printing
            }

            /**
             * Overrides the println method to add indentation before printing the object’s string representation.
             *
             * @param x the object to print
             */
            @Override
            public void println(Object x) {
                super.println(INDENT + x); // Add indentation for Object toString()
            }
        });
    }

    /**
     * Reads a single line of input from the user.
     *
     * @return the input provided by the user
     */
    public static String getInput() {
        return scan.nextLine();
    }

    /**
     * Prints a horizontal line, followed by the provided messages, and then another horizontal line.
     * Each message is printed with indentation.
     *
     * @param msgs the messages to print
     */
    public static void reply(String... msgs) {
        String horizontalLine = "_".repeat(50);
        System.out.println(horizontalLine);
        for (String msg : msgs) {
            System.out.println(msg);
        }
        System.out.println(horizontalLine);
    }
}
