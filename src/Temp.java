import Logger.SmartLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Temp {
    private final String rootDirectory;
    public Temp (String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
    public void writeToTemp(StringBuilder logs) {
        try (FileWriter writer = new FileWriter(rootDirectory + "/temp/temp.txt");) {
            writer.write(logs.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean createTempFile() {
        File directory = new File(rootDirectory + "/temp");

        if (!directory.mkdir()) {
            return false;
        }

        File temp = new File(directory, "temp.txt");

        try {
            return temp.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return  false;
        }
    }
}
