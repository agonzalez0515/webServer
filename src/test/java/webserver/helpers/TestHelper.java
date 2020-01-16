package webserver.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestHelper {

    public static void copyFile(String copyFromPath, String copyToPath) {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            File inFile = new File(copyFromPath);
            File outFile = new File(copyToPath);

            in = new FileInputStream(inFile);
            out = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];

            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();

            System.out.println("File copied successfully!!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearFile(String filePath) {
        try {
            new FileOutputStream(filePath).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}