package webserver.request;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

import webserver.request.Request;

public class RequestTest {

    @Test
    public void itReturnsRequestMethod() throws IOException {
        Request request = new Request.Builder("GET", null).build();

        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testItReturnsRequestPath() throws IOException {
        Request request = new Request.Builder(null, "/hey").build();

        assertEquals("/hey", request.getPath());
    }

    @Test
    public void itReturnsNullWhenRequestMethodNotProvided() throws IOException {
        Request request = new Request.Builder(null, null).build();

        assertEquals(null, request.getMethod());
        assertEquals(null, request.getPath());
    }

    @Test
    public void itReturnsHeadersWithValues() throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header name", "header value");
        Request request = new Request.Builder("GET", "/hey").withHeaders(headers).build();
        String value = request.getHeaders().get("header name") ;

        assertEquals("header value", value);
    }

    @Test
    public void testItReturnsTheRequestBody()throws IOException {
        Request request = new Request.Builder("GET", "/hey").withBody("this is the body").build();

        assertEquals("this is the body", request.getBody());
    }
}
