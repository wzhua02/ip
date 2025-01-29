package baymax.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class StorageTest {
    private Storage storage;
    private Path testFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        testFile = tempDir.resolve("tasks.txt");
        storage = new Storage(testFile);
    }

    @Test
    void testFileCreation() {
        assertTrue(Files.exists(testFile));
    }

    @Test
    void testSaveAndLoad() {
        ArrayList<String> tasks = new ArrayList<>(List.of("Task 1", "Task 2", "Task 3"));
        storage.save(tasks);

        ArrayList<String> loadedTasks = storage.load();
        assertEquals(tasks, loadedTasks);
    }

    @Test
    void testLoadFromEmptyFile() {
        ArrayList<String> loadedTasks = storage.load();
        assertTrue(loadedTasks.isEmpty());
    }
}

