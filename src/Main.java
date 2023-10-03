import Logger.SmartLogger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static String rootDirectory = "D:/Games";
    static String gameTitle = "NeedForSpeed";
    static StringBuilder logs;
    static SmartLogger logger;
    static List<String> directoryNames = Arrays.asList(
            "src",
            "res",
            "savegames",
            "src/main",
            "src/test",
            "res/drawables",
            "res/vectors",
            "res/icons"
    );
    static List<String> fileNames = Arrays.asList("src/main/Main.java", "src/main/Utils.java");

    public static void main(String[] args) {
        logs = new StringBuilder();
        logger = new SmartLogger();

        Temp temp = new Temp(rootDirectory + "/" + gameTitle);
        FileHandler fileHandler = new FileHandler(rootDirectory + "/" + gameTitle, logger);

        logs.append(fileHandler.createRootDirectory());

        boolean isTempCreated = temp.createTempFile();

        if (!isTempCreated) {
            System.out.println("Файл temp.txt не удалось создать, дальнейшая установка невозможна");
            return;
        }

        logs.append(logger.log("Файл temp/temp.txt создан"));

        logs.append(fileHandler.createDirectories(directoryNames));

        logs.append(fileHandler.createFiles(fileNames));

        temp.writeToTemp(logs);

        Game game = new Game(rootDirectory + "/" + gameTitle + "/" + "savegames");

        Map<String, GameProgress> saveGameMap = new HashMap<>();

        saveGameMap.put("save1.dat", new GameProgress(100, 20, 10, 20));
        saveGameMap.put("save2.dat", new GameProgress(88, 10, 40, 50));
        saveGameMap.put("save3.dat", new GameProgress(100, 1, 1, 0));

        for (Map.Entry<String, GameProgress> entry : saveGameMap.entrySet()) {
            game.saveGameProgress(entry.getKey(), entry.getValue());
        }

        game.zipFiles("zip.zip", saveGameMap);

        for (Map.Entry<String, GameProgress> entry : saveGameMap.entrySet()) {
            game.removeGameProgress(entry.getKey());
        }

        game.openZip("zip.zip");

        System.out.println(game.openProgress("save1.dat"));
    }


}