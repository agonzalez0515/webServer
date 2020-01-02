package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

public class SocketIO {
    public BufferedReader createSocketReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter createSocketWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }
}
