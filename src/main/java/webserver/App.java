package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static ServerSocket createServerSocket(int portNum) throws IOException {
        return new ServerSocket(portNum);
    }

    public static Socket createClientConnection(ServerSocket server) throws IOException {
        return server.accept();
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
