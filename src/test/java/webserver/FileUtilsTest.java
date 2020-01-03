package webserver;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileUtilsTest {

    @Test
    public void itCreatesAReaderFromAPath() throws FileNotFoundException {
        FileUtils files = new FileUtils();
        Reader actual = files.fileReader("/src/test/resources/testJson.json");
        int read = 0;
        try {
            read = actual.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(read);
    }

    // public void itCreatesAFileWriter() throws IOException {
    //     FileUtils files = new FileUtils();
    //     FileWriter actual = files.fileWriter("/src/test/resources/testJson.json");

       
    // }
}
