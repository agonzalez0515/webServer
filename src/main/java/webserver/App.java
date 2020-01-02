package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        SocketCreator socketCreator = new SocketCreator();
        try {
            ServerSocket server = socketCreator.createServerSocket();

            while (true) {
                Socket client = socketCreator.createClientConnection(server);
                Thread serverThread = new Thread(new SocketHandler(client));
                serverThread.start();
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}


