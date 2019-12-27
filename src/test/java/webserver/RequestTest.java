package webserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestTest {
    Request request;
    BufferedReader in;

    @Test
    public void testItReturnsRequestMethod() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET / HTTP/1.1\nHost: 1.1.1\r\n".getBytes())));
        request = new Request(in);
        request.parse();

        assertEquals("GET", request.getRequestMethod());
    }

    @Test
    public void testItReturnsRequestPath() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /index.html HTTP/1.1\nHost: 1.1.1\r\n".getBytes())));
        request = new Request(in);
        request.parse();

        assertEquals("/index.html", request.getRequestPath());
    }

    @Test
    public void testReturnsTrueIfAInitialLineIsParsable() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET / HTTP/1.1\nHost: 1.1.1\r\n".getBytes())));
        request = new Request(in);

        assertEquals(true, request.parse());
    }

    @Test
    public void testReturnsFalseIfAInitialLineIsNotParsable() throws IOException{
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\r\n".getBytes())));
        request = new Request(in);

        assertEquals(false, request.parse());
    }

    @Test
    public void testReturnsTrueIfHeadersAreParsable() throws IOException {
        String testInput = "GET / HTTP/1.1\nHost: localhost:5000";
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
        request = new Request(in);

        assertEquals(true, request.parse());
    }

    @Test
    public void testReturnsFalseIfHeadersAreNotParsable() throws IOException {
        String testInput = "GET / HTTP/1.1\ncontent-length 100";
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
        request = new Request(in);

        assertEquals(false, request.parse());
    }

    @Test
    public void testReturnsFalseIfAnyHeaderIsNotParsable() throws IOException {
        String testInput = "GET / HTTP/1.1\ncontent-length: 100\nhost 1.1.1";
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
        request = new Request(in);

        assertEquals(false, request.parse());
    }

    @Test
    public void testItReturnsTheRequestBody()throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /index.html HTTP/1.1\nHost: 1.1.1\r\n".getBytes())));
        request = new Request(in);
        request.parse();

        assertEquals("/index.html", request.getRequestPath());
    }

    @Test
    public void testItHasABody() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("POST /todo/new HTTP/1.1\nContent-Length: 18\r\n\r\ntitle=hey&text=bye".getBytes())));
        request = new Request(in);
        request.parse();
        var expected = Map.of("title", "hey", "text", "bye");

        assertEquals(expected, request.getRequestBody());
    }
}
