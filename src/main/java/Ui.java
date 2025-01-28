import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Ui {
    private static final String INDENT = "    ";
    private final Scanner scan;
    private final PrintStream out;

    public Ui() {
        this(System.in, System.out);
    }

    public Ui(InputStream in, PrintStream out) {
        scan = new Scanner(in);
        System.setOut(new PrintStream(out) {
            @Override
            public void println(String x) {
                super.println(INDENT + x); // Add indentation before printing
            }

            @Override
            public void println(Object x) {
                super.println(INDENT + x); // Add indentation for Object toString()
            }
        });
        this.out = out;
    }

    public static String getInput() {
        return scan.nextLine();
    }

    public static void reply(String... msgs) {
        String horizontal_line = "_".repeat(50);
        System.out.println(horizontal_line);
        for (String msg : msgs) {
            System.out.println(msg);
        }
        System.out.println(horizontal_line);
    }
}
