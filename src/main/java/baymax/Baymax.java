package baymax;

import java.nio.file.Path;
import java.nio.file.Paths;

import baymax.io.Storage;
import baymax.io.Ui;
import baymax.task.TaskList;
import baymax.util.Parser;

public class Baymax {
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Baymax(Path filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.reply("Hello! I'm Baymax", "How can I assist you?");
        String input = ui.getInput();
        while (!input.startsWith("bye")) {
            Parser.parse(input, ui, tasks, storage);
            input = ui.getInput();
        }
        ui.reply("Goodbye! *slowly deflates*");
    }

    public static void main(String[] args) {
        new Baymax(FILE_PATH).run();
    }
}
