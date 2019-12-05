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
    String htmlString = controller.index();

    assertThat(htmlString, containsString("Angie"));
  }

  @Test
  public void testItReturnsHealthCheck() throws IOException {
    String htmlString = controller.healthCheck();

    assertThat(htmlString, containsString("Health Check"));
  }

  @Test
  public void testItReturnsAllListings() throws IOException {
    String htmlString = controller.listingList();

    assertThat(htmlString, containsString("todo-list"));
  }

  @Test
  public void testItReturnsOneListing() throws IOException {
    String htmlString = controller.listingDetail(1);

    assertThat(htmlString, containsString("listing-detail"));
  }

  @Test
  public void testItReturns404PageWhenDetailNotFound() throws IOException {
    String htmlString = controller.listingDetail(2);

    assertThat(htmlString, containsString("File Not Found"));
  }
}