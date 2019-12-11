package webserver;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.containsString;


public class RouterTest {
  Router router;
  @Spy Routes routesSpy = new Routes();

  @Before
  public void init() {
      MockitoAnnotations.initMocks(this);
      router = new Router(routesSpy);
  }

  @Test
  public void testItRoutesGetRequestsToController() throws IOException {
    router.route("/", "get");

   verify(routesSpy).get("/"); 
  }

  @Test
  public void testItRoutesHeadRequestsToController() throws IOException {
    router.route("/", "head");

   verify(routesSpy).head("/"); 
  }

  @Test
  public void testItRoutesAllCapGETRequestsToController() throws IOException {
    router.route("/", "GET");

   verify(routesSpy).get("/"); 
  }

  @Test
  public void testItRoutesAllCapHEADRequestsToController() throws IOException {
    router.route("/", "HEAD");

   verify(routesSpy).head("/"); 
  }

  @Test
  public void testItReturnsAResponseString() throws IOException {
    String response = router.route("/", "get");

    assertThat(response, containsString("HTTP/1.1 200 OK"));
  }
}
