package webserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketIO {
    public static Scanner createSocketReader(Socket socket) throws IOException {
        return new Scanner(new InputStreamReader(socket.getInputStream()));
    }

    public static PrintWriter createSocketWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    public static String readFromInputStream(Scanner input) {
        return input.nextLine();
    }

    public static void writeToOutputStream(PrintWriter output, String data) {
        output.println(data);
    }
}
