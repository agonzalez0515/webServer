package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        String port = System.getProperty("port");
        String directory = System.getProperty("directory"); 

        if (directory == null) {
            directory = "";
        }

        ServerSocket server;

        SocketCreator socketCreator = new SocketCreator();
        
        try {

            if (port.equals("")) {
                server = socketCreator.createServerSocket();
            } else {
                int portNum = Integer.parseInt(port);
                server = socketCreator.createServerSocketWithPort(portNum);
            }

            while (true) {
                Socket client = socketCreator.createClientConnection(server);
                Thread serverThread = new Thread(new SocketHandler(client, directory));
                serverThread.start();
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}


