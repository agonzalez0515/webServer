package webserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponseTest {
    Response response;
    PrintWriter out;

    @Mock
    Socket client;

    @Before
    public void init() {
        response = new Response(out, "GET", "/");
    }

    @Test
    public void testItReturnsHTMLFromAPath() throws IOException {
        String path = "/hello.html";
        assertEquals("<html><body><h1>Hello World</h1></body></html>", response.getHTML(path));
    }

    @Test
    public void testItReturnsFalseIfPathIsInvalid() throws IOException {
        Response res = new Response(out, "Get", "/world.html");
        String path = "/world.html";
        assertEquals("File Not Found", res.getHTML(path));
    }

    @Test
    public void testSetsInitialResponseLineForOkMessage() {
        assertEquals("HTTP/1.1 200 OK", response.setInitialResponseLine(200));
    }

    @Test
    public void testSetsInitialResponseLineForErrorMessage() {
        assertEquals("HTTP/1.1 404 Not Found", response.setInitialResponseLine(404));
    }

    @Test
    public void testItCreatesHeaderLines() {
        Map<String, String> expectedHeaders = Map.of("Connection", "Keep-Alive","Content-Type", "text/html");

        response.createHeader("Connection", "Keep-Alive");
        response.createHeader("Content-Type", "text/html");

        assertEquals(expectedHeaders, response.headers);
    }



}
