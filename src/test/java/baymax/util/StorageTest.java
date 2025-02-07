package baymax.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final Path TEST_FILE_PATH = Path.of("test_data", "test_tasks.txt");
    private Storage storage;

    @BeforeEach
    void setUp() throws IOException {
        File testFile = new File(TEST_FILE_PATH.toString());
        testFile.getParentFile().mkdirs();
        testFile.createNewFile();
        storage = new Storage();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
        Files.deleteIfExists(TEST_FILE_PATH.getParent());
    }

    @Test
    void load_existingFileWithTasks_returnsTaskList() throws IOException {
        List<String> sampleTasks = List.of("Task 1", "Task 2", "Task 3");
        try (FileWriter writer = new FileWriter(TEST_FILE_PATH.toFile())) {
            for (String task : sampleTasks) {
                writer.write(task + "\n");
            }
        }

        ArrayList<String> loadedTasks = storage.load();
        assertEquals(sampleTasks, loadedTasks);
    }

    @Test
    void load_nonExistingFile_returnsEmptyList() {
        Storage newStorage = new Storage();
        ArrayList<String> tasks = newStorage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void save_validTaskList_savesSuccessfully() {
        ArrayList<String> tasksToSave = new ArrayList<>(List.of("Task A", "Task B"));
        storage.save(tasksToSave);

        ArrayList<String> loadedTasks = storage.load();
        assertEquals(tasksToSave, loadedTasks);
    }
}
