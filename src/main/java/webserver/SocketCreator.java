package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketCreator {
    public ServerSocket createServerSocketWithPort(int portNum) throws IOException {
        return new ServerSocket(portNum);
    }

    public ServerSocket createServerSocket() throws IOException {
        int portNum = getPortNumber();
        return new ServerSocket(portNum);
    }

    public Socket createClientConnection(ServerSocket server) throws IOException {
        return server.accept();
    }

    private static int getPortNumber() { //FIXME move this out to cli parser or setup class
        int portNumber;
        try {
            portNumber = Integer.parseInt(System.getenv("PORT"));
        } catch (NumberFormatException e) {
            portNumber = 5000;
        }
        return portNumber;
    }
}
