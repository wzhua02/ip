package baymax.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles reading from and writing to a file that stores tasks.
 * It provides methods to load data from the file and save data to the file.
 */
public class Storage {
    private File dataFile; // The file where tasks are stored

    /**
     * Constructs a Storage object with the specified file path.
     * If the file does not exist, it creates the file and its parent directories.
     *
     */
    public Storage(Path filePath) {
        try {
            dataFile = new File(filePath.toString());
            if (!dataFile.exists()) {
                File dataDir = new File(filePath.getParent().toString());
                dataDir.mkdirs();
                dataFile.createNewFile();
                System.out.println("\"tasks.txt\" not found in directory. Created a new one.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not Found Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Create File Error: " + e.getMessage());
        }
    }

    /**
     * Loads the list of tasks from the file.
     * Each line in the file represents a single task.
     *
     * @return an ArrayList containing the tasks read from the file
     */
    public ArrayList<String> load() {
        ArrayList<String> listFromFile = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                listFromFile.add(line);
            }
        } catch (IOException e) {
            System.err.println("File Read Error: " + e.getMessage());
        }
        return listFromFile;
    }

    /**
     * Saves a list of tasks to the file, each task on a new line.
     *
     * @param saveList an ArrayList containing the tasks to be saved to the file
     */
    public void save(ArrayList<String> saveList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
            saveList.forEach(writer::println);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
