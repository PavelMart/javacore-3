import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Game {
    public String pathToSave;
    public Game (String pathToSave) {
        this.pathToSave = pathToSave;
    }
    public void saveGameProgress(String saveTitle, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(pathToSave + "/" + saveTitle); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public GameProgress openProgress(String saveName) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathToSave + "/" + saveName))) {
            return (GameProgress) ois.readObject();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void zipFiles(String archiveName, Map<String, GameProgress> saveGameMap) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathToSave + "/" + archiveName))) {
            for (Map.Entry<String, GameProgress> mapEntry : saveGameMap.entrySet()) {
                ZipEntry entry = new ZipEntry(mapEntry.getKey());
                zout.putNextEntry(entry);
                try (FileInputStream fis = new FileInputStream(pathToSave + "/" + mapEntry.getKey());) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
                catch (IOException ex) {;
                    System.out.println(ex.getMessage());
                }

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void openZip(String zipPath) {
        try (ZipInputStream zist = new ZipInputStream(new FileInputStream(pathToSave + "/" + zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zist.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(pathToSave + "/" + name);
                for (int i = zist.read(); i != -1; i = zist.read()) {
                    fout.write(i);
                }
                fout.flush();
                zist.closeEntry();
                fout.close();
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeGameProgress(String saveTitle) {
            File file = new File(pathToSave + "/" + saveTitle);
            if (file.delete()) {
                System.out.println("Файл удален");
            } else {
                System.out.println("Файл не был удален");
            }
    }
}
