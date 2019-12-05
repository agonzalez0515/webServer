package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

class SocketHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private SocketIO io = new SocketIO();
    private Socket client;

    public SocketHandler(Socket client) throws  IOException {
        this.in = io.createSocketReader(client);
        this.out = io.createSocketWriter(client);
        this.client = client;
    }

    public void run() {
        int statusCode;
        Request request = new Request(in);

        try {
            request.parse();
            String path = request.getRequestPath();
            String method = request.getRequestMethod();
            String htmlToSend = HtmlHandler.getHtml(path);
            
            if (HtmlHandler.fileExists(path)) {
                statusCode = 200;
            } else {
                statusCode = 404;
            }

            Response response = new Response(out, method, htmlToSend, statusCode);
            response.send();

        } catch (IOException e) {
            e.printStackTrace();
        } finally  {
            closeStreams();
          }
    }

    private void closeStreams() {
        try {
            if (out != null) { out.close(); }
            if (in != null) { in.close(); }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
