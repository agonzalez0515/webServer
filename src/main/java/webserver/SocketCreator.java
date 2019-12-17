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

    private static int getPortNumber() {
        int portNumber;
        try {
            portNum  
