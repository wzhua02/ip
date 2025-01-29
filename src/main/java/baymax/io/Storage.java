package baymax.io;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles reading from and writing to a file that stores tasks.
 * It provides methods to load data from the file and save data to the file.
 */
public class Storage {
    private File dataFile;  // The file where tasks are stored

    /**
     * Constructs a Storage object with the specified file path.
     * If the file does not exist, it creates the file and its parent directories.
     *
     * @param filePath the path to the file where tasks will be stored
     */
    public Storage(Path filePath) {
        try {
            dataFile = new File(filePath.toString());
            if (!dataFile.exists()) {
                File dataDir = new File(filePath.getParent().toString());
                dataDir.mkdirs();  // Create parent directories if they don't exist
                dataFile.createNewFile();  // Create the file
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
        ArrayList<String> returnList = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(dataFile);  // Scanner to read the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();  // Read each line
                returnList.add(line);  // Add the line (task) to the list
            }
        } catch (IOException e) {
            System.err.println("Scanner Error: " + e.getMessage());
        }
        return returnList;  // Return the list of tasks
    }

    /**
     * Saves a list of tasks to the file, each task on a new line.
     *
     * @param saveList an ArrayList containing the tasks to be saved to the file
     */
    public void save(ArrayList<String> saveList) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(dataFile));  // Writer to write to the file
            for (String task : saveList) {
                writer.println(task);  // Write each task to the file
            }
            writer.close();  // Close the writer after saving
        } catch (IOException e) {
            System.err.println(e.getMessage());  // Print any error that occurs during saving
        }
    }
}
