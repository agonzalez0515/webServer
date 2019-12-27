package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

import webserver.request.Parser;
import webserver.request.Request;
import webserver.routes.AppRoutes;
import webserver.routes.TodoRoutes;

class SocketHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private SocketIO io = new SocketIO();
    private Socket client;
    private String directory;

    public SocketHandler(Socket client, String directory) throws  IOException {
        this.in = io.createSocketReader(client);
        this.out = io.createSocketWriter(client);
        this.client = client;
        this.directory = directory;
    }

    public void run() {
        Parser parser= new Parser();
        Router router = new Router();
        TodoRoutes todoRoutes = new TodoRoutes();
        AppRoutes appRoutes = new AppRoutes();
        
        todoRoutes.addRoutes(router);
        appRoutes.addRoutes(router);

        try {
            Request request = parser.parse(in);
            String response = router.route(request);
            out.println(response);

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
