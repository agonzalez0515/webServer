package webserver;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        int index;
        ServerSocket server;
        HashMap<String, String> cliArgs = new HashMap<String, String>();

        for (index = 0; index < args.length; ++index) {
            System.out.println("args[" + index + "]: " + args[index]);
            String[] setting = args[index].split("=");
            cliArgs.put(setting[0].toLowerCase(), setting[1].toLowerCase());
        }

        SocketCreator socketCreator = new SocketCreator();
        try {

            if (cliArgs.containsKey("port")) {
                int portNum = Integer.parseInt(cliArgs.get("port"));
                server = socketCreator.createServerSocketWithPort(portNum);
            } else {
                server = socketCreator.createServerSocket();
            }

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


