package webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class FileUtils {
    private final String USER_DIR = "user.dir";

    public Reader fileReader(String filePath) throws FileNotFoundException {
        File file = fileFromDirectory(filePath);
        return new FileReader(file);
    }

    public FileWriter fileWriter(String filePath) throws IOException {
        File file = fileFromDirectory(filePath);
        return new FileWriter(file);
    }

    private File fileFromDirectory(String filePath) {
        String path = System.getProperty(USER_DIR) + filePath;
        return new File(path);
    }
}
