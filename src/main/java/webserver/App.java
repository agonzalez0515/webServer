package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class App {    
    private static SocketCreator socketCreator = new SocketCreator();
    private static SystemUtils system = new SystemUtils();
    private static Setup setup = new Setup(system);

    public static void main(String[] args) {
        int portNum = setup.createPortNumber(system.getPortCliArg());
        String directory = setup.createDirectory(system.getDirectoryCliArg());

        try {
            ServerSocket server = socketCreator.createServerSocket(portNum);

            while (true) {
                Socket client = socketCreator.createClientConnection(server);
                Thread serverThread = new Thread(new SocketHandler(client, directory));
                serverThread.start();
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
