import Logger.SmartLogger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    private final String rootDirectory;
    private final SmartLogger logger;

    public FileHandler(String rootDirectory, SmartLogger logger) {
        this.rootDirectory = rootDirectory;
        this.logger = logger;
    }

    public String createRootDirectory() {
        File directory = new File(rootDirectory);
        return createDirectory(directory);
    }

    public String createDirectory(File directory) {
        if (directory.mkdir()) {
            return logger.log("Директория " + directory.getName() + " успешно создана");
        } else {
            return logger.log("Ошибка при создании директории: " + directory.getName());
        }
    }

    public String createFile(File file) {
        try {
            if (file.createNewFile()) {
                return logger.log("Файл " + file.getName() + " успешно создан");
            } else {
                return logger.log("Ошибка при создании файла: " + file.getName());
            }
        } catch (IOException ex) {
            return logger.log("Ошибка при создании файла: " + file.getName() + " - " + ex.getMessage());
        }
    }

    public String createDirectories(List<String> directoryNames) {
        StringBuilder result = new StringBuilder();
        for (String name : directoryNames) {
            File directory = new File(rootDirectory + "/" + name);
            result.append(createDirectory(directory));
        }
        return result.toString();
    }

    public String createFiles(List<String> fileNames) {
        StringBuilder result = new StringBuilder();
        for (String name : fileNames) {
            File file = new File(this.rootDirectory + "/" + name);
            result.append(createFile(file));
        }
        return result.toString();
    }
}
