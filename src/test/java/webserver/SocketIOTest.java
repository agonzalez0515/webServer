package webserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.Socket;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SocketIOTest {
    SocketIO io;

    @Mock
    Socket clientSocket;

    @Mock
    InputStream inputStream;

    @Mock
    OutputStream outputStream;

    @Before
    public void init() {
        io = new SocketIO();
    }

    @Test
    public void testSocketInputGetsCreated() throws IOException {
        when(clientSocket.getInputStream()).thenReturn(inputStream);

        assertNotNull(io.createSocketReader(clientSocket));
    }

    @Test
    public void testSocketOutputGetsCreated() throws IOException {
        when(clientSocket.getOutputStream()).thenReturn(outputStream);

        assertNotNull(io.createSocketWriter(clientSocket));
    }
}
