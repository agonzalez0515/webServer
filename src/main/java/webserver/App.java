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


    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        try {

            int portNumber = Integer.parseInt(System.getenv("PORT"));
            ServerSocket server = createServerSocket(portNumber);

            Socket client = createClientConnection(server);

            Thread serverThread = new Thread(new SocketHandler(client));
            serverThread.start();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}


class SocketHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;

    public SocketHandler(Socket client) {
        try {
            this.in = SocketIO.createSocketReader(client);
            this.out = SocketIO.createSocketWriter(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("running");
        try {
            System.out.println(in.readLine());
        } catch(IOException e) {
            e.printStackTrace();
        }

        while (in != null) {
            Request request = new Request(in);
            try {
                if (request.parse())  {
                    String method = request.method;
                    String path = request.path;
                    Response response = new Response(out, method, path);
                    response.send();

                } else {
                    String responseLine = "HTTP/1.1 " + 500 + " " + "Unable to parse request" + "\r\n\r\n";
                    out.println(responseLine);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}