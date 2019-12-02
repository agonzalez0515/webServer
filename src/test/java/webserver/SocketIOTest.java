package webserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SocketIOTest {
    @Mock
    Socket clientSocket;

    @Mock
    InputStream inputStream;

    @Mock
    OutputStream outputStream;

    @Test
    public void testSocketInputGetsCreated() throws IOException {
        when(clientSocket.getInputStream()).thenReturn(inputStream);

        assertNotNull(SocketIO.createSocketReader(clientSocket));
    }

    @Test
    public void testSocketOutputGetsCreated() throws IOException {
        when(clientSocket.getOutputStream()).thenReturn(outputStream);

        assertNotNull(SocketIO.createSocketWriter(clientSocket));
    }

    @Test
    public void testReadClientStreamLine() throws IOException{
        String inputString = "Hello";
        BufferedReader input = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputString.getBytes())));

        assertEquals("Hello", SocketIO.readFromInputStream(input));
    }

    @Test
    public void testWriteClientStreamLine() {
        String inputString = "World";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outContent, true);

        SocketIO.writeToOutputStream(printWriter, inputString);

        assertEquals("World\n", outContent.toString());
    }
}

