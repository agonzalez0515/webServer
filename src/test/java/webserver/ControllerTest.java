package webserver;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class ControllerTest {
  Controller controller;

  @Before
  public void init() {
     controller = new Controller();
  }

  @Test
  public void testItReturnsHomePage() throws IOException {
    String htmlString = controller.getIndex();

    assertThat(htmlString, containsString("Angie"));
  }

  @Test
  public void testItReturnsHealthCheck() throws IOException {
    String htmlString = controller.getHealthCheck();

    assertThat(htmlString, containsString("Health Check"));
  }

  @Test
  public void testItReturnsAllListings() throws IOException {
    String htmlString = controller.getListingList();

    assertThat(htmlString, containsString("todo-list"));
  }

  @Test
  public void testItReturnsOneListing() throws IOException {
    String htmlString = controller.getListingDetail(1);

    assertThat(htmlString, containsString("listing-detail"));
  }

  @Test
  public void testItReturns404WhenPageNotFound() throws IOException{
    String htmlString = controller.getNotFound();

    assertThat(htmlString, containsString("File Not Found"));
  }

  @Test
  public void testItReturns404PageWhenDetailNotFound() throws IOException {
    String htmlString = controller.getListingDetail(2000);

    assertThat(htmlString, containsString("File Not Found"));
  }

  @Test
  public void testItReturnsResponseFromCustomDirectory() throws IOException {
    String htmlString = controller.getDirectoryFile("/public/example.txt");

    assertThat(htmlString, containsString("This is an example file"));
  }

  @Test
  public void testItReturns404WhenFileFromCustomDirectoryNotFound() throws IOException {
    String htmlString = controller.getDirectoryFile("/public/hey.txt");

    assertThat(htmlString, containsString("File Not Found"));
  }
}