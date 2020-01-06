package webserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class ResponseBody {
  public static boolean fileExists(String path) {
    Path parsedPath = createFilePath(path);
    return Files.exists(parsedPath);
  }

  public static String getHtml(String path) throws IOException {
    Path parsedPath = createFilePath(path);
    try(FileInputStream file = getFile(parsedPath)) {
        return createHTMLString(file);
    }
  }

  private static Path createFilePath(String path) {
      return FileSystems.getDefault().getPath(path.substring(1));
  }

  private static FileInputStream getFile(Path path) {
      try {
          return new FileInputStream(path.toString());
      } catch (FileNotFoundException e) {
          Path errorFile = createFilePath("/404.html");
          return getFile(errorFile);
      }
  }

  private static String createHTMLString(FileInputStream file) throws IOException {
      StringBuilder buf = new StringBuilder();

      int c;
      while ((c = file.read()) != -1) {
          buf.append((char) c);
      }
      return buf.toString();
  }
}
