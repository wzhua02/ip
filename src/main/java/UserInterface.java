import java.io.PrintStream;
import java.util.Scanner;

public class UserInterface {
    private static final String INDENT = "    ";
    private final Scanner scan;

    public UserInterface() {
        scan = new Scanner(System.in);
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
    }

    public String getInput() {
        return scan.nextLine();
    }

    public void reply(String msg, String... otherMsgs) {
        String horizontal_line = "_".repeat(50);
        System.out.println(horizontal_line);
        System.out.println(msg);
        for (String msgs : otherMsgs) {
            System.out.println(msgs);
        }
        System.out.println(horizontal_line);
    }
}
