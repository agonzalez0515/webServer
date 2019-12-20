package webserver;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class RoutesTest {
    Controller controller;
    Routes routes;

    @Before
    public void init() {
        controller = new Controller();
        routes = new Routes(controller, "");
    }

    @Test
    public void testItReturnsResponseForGetRoutesThatExist() throws IOException {
        String response = routes.get("/todo");

        assertThat(response, containsString("todo-list"));
    }

    @Test
    public void testItReturnsErrorResponseForGetRoutesThatDoNotExist() throws IOException {
        String response = routes.get("/hey");

        assertThat(response, containsString("File Not Found"));
    }

    @Test
    public void testItReturnsResponseForHeadRoutesThatExist() throws IOException {
        String response = routes.head("/todo");

        assertThat(response, containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void testItReturnsErrorResponseForHeadRoutesThatDoNotExist() throws IOException {
        String response = routes.head("/hey");

        assertThat(response, containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void testItReturnsResponseForFileInDirectoryButNotInRoutesMap() throws IOException {
        Routes routesWithDirectory = new Routes(controller, "public");
        String response = routesWithDirectory.get("/public/example.txt");

        assertThat(response, containsString("This is an example file"));
    }
}
