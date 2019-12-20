package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketCreator {
    public ServerSocket createServerSocket(int portNum) throws IOException {
        return new ServerSocket(portNum);
    }

    public Socket createClientConnection(ServerSocket server) throws IOException {
        return server.accept();
    }

}
