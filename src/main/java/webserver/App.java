package webserver;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.PrintWriter;
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



    public static void run() throws  IOException {
        System.out.println("running");
        int portNumber = Integer.parseInt(System.getenv("PORT"));
        System.out.println(portNumber);
        ServerSocket server = createServerSocket(portNumber);

        Socket client = createClientConnection(server);
        BufferedReader in = SocketIO.createSocketReader(client);
        PrintWriter out = SocketIO.createSocketWriter(client);

        while (client != null) {
            Request request = new Request(in);
            if (request.parse())  {
                System.out.println("in here");
                String method = request.method;
                String path = request.path;
                System.out.println("in app " + path);
                Response response = new Response(out, method, path);
                response.send();

            } 
        }
    }


    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        try {
            run();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
