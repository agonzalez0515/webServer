package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private static final String PORT = "port";
    private static final String DIRECTORY = "directory";
    private static final String NO_DIRECTORY_SPECIFIED = "";
    private static final String NO_PORT_SPECIFIED = "";

    private static SocketCreator socketCreator = new SocketCreator();
    private static ServerSocket server;

    public static void main(String[] args) {
        String port = System.getProperty(PORT);
        String directory = System.getProperty(DIRECTORY); 

        if (directory == null) { directory = NO_DIRECTORY_SPECIFIED; }

        // int port = CliParser.getPort();
        // String directory = CliParser.getDirectory();

        
        try {
            if (port.equals(NO_PORT_SPECIFIED)) {
                server = socketCreator.createServerSocket();
            } else {
                int portNum = Integer.parseInt(port);
                server = socketCreator.createServerSocketWithPort(portNum);
            }
            
            // server = socketCreator.createServerSocketWithPort(port);


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
