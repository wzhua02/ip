package baymax.io;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    File dataFile;
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

    public ArrayList<String> load() {
        ArrayList<String> returnList = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                returnList.add(line);
            }
        } catch (IOException e) {
            System.err.println("Scanner Error: " + e.getMessage());
        }
        return returnList;
    }

    public void save(ArrayList<String> saveList) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(dataFile));
            for (String task : saveList) {
                writer.println(task);
            }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
