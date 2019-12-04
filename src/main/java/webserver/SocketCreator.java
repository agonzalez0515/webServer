package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketCreator {
    public static ServerSocket createServerSocket() throws IOException {
        int portNum = getPortNumber();
        return new ServerSocket(portNum);
    }

    public static Socket createClientConnection(ServerSocket server) throws IOException {
        return server.accept();
    }

    private static int getPortNumber() {
        int portNumber;
        try {
            portNumber = Integer.parseInt(System.getenv("PORT"));
        } catch (NumberFormatException e) {
            portNumber = 5000;
        }
        return portNumber;
    }
}
