import java.nio.file.Path;
import java.nio.file.Paths;

public class Baymax {
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private final Storage storage;
    private final TaskList tasks;

    public Baymax(Path filePath) {
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        Ui.reply("Hello! I'm Baymax", "How can I assist you?");
        String input = Ui.getInput();
        while (!input.startsWith("bye")) {
            Parser.parse(input, tasks, storage);
            input = Ui.getInput();
        }
        Ui.reply("Goodbye! *slowly deflates*");
    }

    public static void main(String[] args) {
        new Baymax(FILE_PATH).run();
    }
}
