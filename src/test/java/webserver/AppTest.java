package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {
    App app;

    @Mock
    ServerSocket mockServer;

    @Before
    public void init() {
        app = new App();
    }

    @Test public void testAppHasAGreeting() {
        assertNotNull("app should have a greeting", app.getGreeting());
    }

    @Test
    public void testServerSocketWithSpecificPortGetsCreated() throws IOException {
        final int testPort = 5000;
        ServerSocket testServerSocket = app.createServerSocket(testPort);

        assertEquals(testServerSocket.getLocalPort(), testPort);
        testServerSocket.close();
    }

    @Test
    public void testAcceptsConnection() throws IOException {
        when(mockServer.accept()).thenReturn(new Socket());

        assertNotNull(app.createClientConnection(mockServer));
    }


}

// 1. Read HTTP request from the client socket
// 2. Prepare an HTTP response
// 3. Send HTTP response to the client
// 4. Close the socket



