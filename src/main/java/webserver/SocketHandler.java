package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

class SocketHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;

    public SocketHandler(Socket client) throws  IOException {
        this.in = SocketIO.createSocketReader(client);
        this.out = SocketIO.createSocketWriter(client);
    }

    public void run() {
        Request request = new Request(in);
        try {
            request.parse();
            Response response = new Response(out, request.path);
            response.send();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
