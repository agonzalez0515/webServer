package webserver;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import webserver.response.ResponseBody;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class SocketHandlerTest {

    @Mock
    Socket clientSocket;

    @Mock
    ResponseBody responseBody;

    @Test
    public void testServerCanSendResponse() throws IOException {
        String inputString = "GET / HTTP/1.1\n";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(clientSocket.getInputStream()).thenReturn(new ByteArrayInputStream(inputString.getBytes()));
        when(clientSocket.getOutputStream()).thenReturn(out);
        SocketHandler socketHandler = new SocketHandler(clientSocket, "public");

        socketHandler.run();

        assertThat(out.toString(), containsString("HTTP/1.1 200 OK") );
    }

    @Test 
    public void testItClosesTheStreams() throws IOException {
      String inputString = "GET / HTTP/1.1\n";
      ByteArrayOutputStream out = spy(new ByteArrayOutputStream());
      Socket spyClientSocket = spy(clientSocket);
      ByteArrayInputStream in = spy(new ByteArrayInputStream(inputString.getBytes()));
      when(spyClientSocket.getInputStream()).thenReturn(in);
      when(spyClientSocket.getOutputStream()).thenReturn(out);
      SocketHandler socketHandler = new SocketHandler(spyClientSocket, "public");

      socketHandler.run();

      verify(out).close();
      verify(in).close();
      verify(spyClientSocket).close();
    }   
}
