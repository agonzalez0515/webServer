package webserver;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SocketCreatorTest {
    @Mock
    ServerSocket mockServerSocket;


    @Test
    public void testServerSocketGetsCreated() throws IOException {
        ServerSocket server = SocketCreator.createServerSocket();

        assertNotNull(server);
        server.close();
    }

    @Test
    public void testServerSocketWithSpecificPortGetsCreated() throws IOException {
        ServerSocket testServerSocket = SocketCreator.createServerSocket();

        assertEquals(testServerSocket.getLocalPort(), 5000);
        testServerSocket.close();
    }

    @Test
    public void testClientSocketGetsCreated() throws IOException {
        when(mockServerSocket.accept()).thenReturn(new Socket());

        assertNotNull(SocketCreator.createClientConnection(mockServerSocket));
    }
}
