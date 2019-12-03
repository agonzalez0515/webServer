package webserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
//public class RequestTest {
//    Request request;
//    BufferedReader in;

//    @Test
//    public void testReturnsTrueIfAInitialLineIsParsable() throws IOException {
//        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET / HTTP/1.1\nHost: 1.1.1\r\n".getBytes())));
//        request = new Request(in);
//
//        assertEquals(true, request.parse() );
//    }

//    @Test
//    public void testReturnsFalseIfAInitialLineIsNotParsable() throws IOException{
//        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\r\n".getBytes())));
//        request = new Request(in);
//
//        assertEquals(false, request.parse());
//    }

//    @Test
//    public void testReturnsTrueIfHeadersAreParsable() throws IOException {
//        String testInput = "GET / HTTP/1.1\nHost: localhost:5000";
//        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
//        request = new Request(in);
//
//        assertEquals(true, request.parse());
//    }
//
//    @Test
//    public void testReturnsFalseIfHeadersAreNotParsable() throws IOException {
//        String testInput = "GET / HTTP/1.1\ncontent-length 100";
//        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
//        request = new Request(in);
//
//        assertEquals(false, request.parse());
//    }
//
//    @Test
//    public void testReturnsFalseIfAnyHeaderIsNotParsable() throws IOException {
//        String testInput = "GET / HTTP/1.1\ncontent-length: 100\nhost 1.1.1";
//        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
//        request = new Request(in);
//
//        assertEquals(false, request.parse());
//    }
//
//    @Test
//    public void testItSetsTheRequestMethod() throws IOException {
//        String testInput = "GET / HTTP/1.1\ncontent-length: 100\n";
//        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
//        request = new Request(in);
//        request.parse();
//
//        assertEquals("GET", request.method);
//    }
//
//    @Test
//    public void testItSetsTheRequestPath() throws IOException {
//        String testInput = "GET /hello.html HTTP/1.1\ncontent-length: 100\n";
//        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testInput.getBytes())));
//        request = new Request(in);
//        request.parse();
//
//        assertEquals("/hello.html", request.path);
//    }
//}


