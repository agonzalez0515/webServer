package webserver;

import java.io.File;

public class FileUtils {
    private final static String USER_DIR = "user.dir";

    public static File fileFromDirectory(String filePath) {
        String path = System.getProperty(USER_DIR) + filePath;
        return new File(path);
    }
}
