package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private static final String PORT = "port";
    private static final String DIRECTORY = "directory";
    private static final String NO_DIRECTORY_SPECIFIED = "";
    private static final String NO_PORT_SPECIFIED = "";

    private static ServerSocket server;
    private static SocketCreator socketCreator = new SocketCreator(); //when do things go here and when should they be declared inside the method?

    public static void main(String[] args) {
        String port = System.getProperty(PORT);
        String directory = System.getProperty(DIRECTORY); 

        if (directory == null) { directory = NO_DIRECTORY_SPECIFIED; }

        
        try {
            if (port.equals(NO_PORT_SPECIFIED)) {
                server = socketCreator.createServerSocket();
            } else {
                int portNum = Integer.parseInt(port);
                server = socketCreator.createServerSocketWithPort(portNum);
            } //FIXME refactor to cli parser class

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
