package webserver;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class HtmlHandlerTest {

  @Test
  public void testReturnsTrueIfAFileExists() {
    assertEquals(true, HtmlHandler.fileExists("/src/test/resources/test.html"));
  }

  @Test
  public void testReturnsFalseIfAFileDoesNotExists() {
    assertEquals(false, HtmlHandler.fileExists("/src/test/resources/hey.html"));
  }

  @Test
  public void testReturnsHtmlStringWhenFileIsFound() throws IOException {
    String htmlString = HtmlHandler.getHtml("/src/test/resources/test.html");

    assertThat(htmlString, containsString("Test File"));
  }

  @Test
  public void testReturns404StringWhenFileIsNotFound() throws IOException {
    String htmlString = HtmlHandler.getHtml("/hey.html");

      assertThat(htmlString, containsString("404"));
  }
}
