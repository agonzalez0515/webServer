package webserver.controllers;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

import webserver.request.Request;

public class AppControllerTest {

  @Mock 
  Request request;

  @Test
  public void testItReturnsHomePage() throws IOException {
    String htmlString = AppController.getIndex.apply(request);

    assertThat(htmlString, containsString("Angie"));
  }

  @Test
  public void testItReturnsHealthCheck() throws IOException {
    String htmlString = AppController.getHealthCheck.apply(request);

    assertThat(htmlString, containsString("Health Check"));
  }
}
