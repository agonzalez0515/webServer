package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class App {

    public static void main(String[] args) {

        try {
            ServerSocket server = SocketCreator.createServerSocket();

            while (true) {
                Socket client = SocketCreator.createClientConnection(server);
                Thread serverThread = new Thread(new SocketHandler(client));
                serverThread.start();
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}


