package baymax.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a user interface (UI) for interacting with the user via standard input and output.
 * This class is responsible for reading user input and formatting output with indentation.
 */
public class Ui {
    private static final String INDENT = "    ";  // Indentation used for formatting output
    private final Scanner scan;  // Scanner instance for reading user input
    private final PrintStream out;  // PrintStream instance for printing output

    /**
     * Constructs a new Ui object with the default input and output streams (System.in, System.out).
     */
    public Ui() {
        this(System.in, System.out);
    }

    /**
     * Constructs a new Ui object with the specified input and output streams.
     *
     * @param in the InputStream to read input from
     * @param out the PrintStream to write output to
     */
    public Ui(InputStream in, PrintStream out) {
        scan = new Scanner(in);
        System.setOut(new PrintStream(out) {
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
        this.out = out;
    }

    /**
     * Reads a single line of input from the user.
     *
     * @return the input provided by the user
     */
    public String getInput() {
        return scan.nextLine();
    }

    /**
     * Prints a horizontal line, followed by the provided messages, and then another horizontal line.
     * Each message is printed with indentation.
     *
     * @param msgs the messages to print
     */
    public void reply(String... msgs) {
        String horizontal_line = "_".repeat(50);  // Horizontal line of 50 underscores
        System.out.println(horizontal_line);
        for (String msg : msgs) {
            System.out.println(msg);
        }
        System.out.println(horizontal_line);
    }
}
