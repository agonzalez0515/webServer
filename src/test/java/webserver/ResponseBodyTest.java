package webserver;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class ResponseBodyTest {

    @Test
    public void testReturnsTrueIfAFileExists() {
        assertEquals(true, ResponseBody.fileExists("/src/test/resources/test.html"));
    }

    @Test
    public void testReturnsFalseIfAFileDoesNotExists() {
        assertEquals(false, ResponseBody.fileExists("/src/test/resources/hey.html"));
    }

    @Test
    public void testReturnsHtmlStringWhenFileIsFound() throws IOException {
        String htmlString = ResponseBody.getHtml("/src/test/resources/test.html");

        assertThat(htmlString, containsString("Test File"));
    }

    @Test
    public void testReturns404StringWhenFileIsNotFound() throws IOException {
        String htmlString = ResponseBody.getHtml("/hey.html");

        assertThat(htmlString, containsString("404"));
    }
}
