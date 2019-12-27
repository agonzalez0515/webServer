package webserver.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert.*;

import webserver.request.Request;

public class ParserTest {
    BufferedReader in;

    @Test
    public void itParsesInitialRequestLined() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("".getBytes())));
        Parser parser = new Parser();
        Request actual = parser.parse(in);

        assertEquals(null, actual);
    }

    @Test
    public void itParsesInitialRequestLineToSetRequestMethod() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET / HTTP/1.1\nHost: 1.1.1\r\n".getBytes())));
        Parser parser = new Parser();
        String actual = parser.parse(in).getMethod();

        assertEquals("GET", actual);
    }

    @Test
    public void itParsesInitialRequestLineToSetRequestPath() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /bye HTTP/1.1\nHost: 1.1.1\r\n".getBytes())));
        Parser parser = new Parser();
        String actual = parser.parse(in).getPath();

        assertEquals("/bye", actual);
    }

    @Test
    public void itParsesInputToSetHeaders() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /bye HTTP/1.1\nHost: 1.1.1\r\nContent-Length: 10\r\n\r\n".getBytes())));
        Parser parser = new Parser();
        HashMap<String, String> actual = parser.parse(in).getHeaders();

        assertTrue(actual.containsKey("Content-Length"));
    }

    @Test
    public void itParsesInputToSetABodyWhenContentLengthIsProvided() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("POST /todo/new HTTP/1.1\nContent-Length: 18\r\n\r\ntitle=hey&text=bye".getBytes())));
        Parser parser = new Parser();
        String actual = parser.parse(in).getBody();

        assertEquals("title=hey&text=bye", actual);
    }

     @Test
    public void itParsesInputAndSetsBodyToNullWhenContentLengthIsNotProvided() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("POST /todo/new HTTP/1.1\r\n".getBytes())));
        Parser parser = new Parser();
        String actual = parser.parse(in).getBody();

        assertEquals(null, actual);
    }
}
