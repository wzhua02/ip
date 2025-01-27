import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Baymax {
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private final Storage storage;
    private final TaskList tasks;
    private final UserInterface ui;

    public Baymax(Path filePath) {
        ui = new UserInterface();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.reply("Hello! I'm Baymax", "How can I assist you?");
        String input = ui.getInput();
        Parser.parse(input, ui, tasks, storage);
        ui.reply("Goodbye! *slowly deflates*");
    }

    public static void main(String[] args) {
        new Baymax(FILE_PATH).run();
    }
}
