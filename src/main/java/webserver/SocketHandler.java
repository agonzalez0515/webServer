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
    private String directory;

    public SocketHandler(Socket client, String directory) throws  IOException {
        this.in = io.createSocketReader(client);
        this.out = io.createSocketWriter(client);
        this.client = client;
        this.directory = directory;
    }

    public void run() {
        Request request = new Request(in);
        Controller controller = new Controller();
        Routes routes = new Routes(controller, directory);
        Router router = new Router(routes);

        try {
            request.parse();
            
            String path = request.getRequestPath();
            String method = request.getRequestMethod();
            String response = router.route(path, method);
            
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
