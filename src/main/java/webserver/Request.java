package webserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
    public static final int REQUEST_METHOD = 0;
    public static final int REQUEST_PATH = 1;
    public BufferedReader in;
    public String method;
    public String path;

    public Request (BufferedReader in) {
        this.in = in;
        this.method = "";
        this.path = "";
    }

    private void getRequestMethod(String[] initialRequestLine) {
        this.method = initialRequestLine[REQUEST_METHOD];
    }

    private void getRequestPath(String[] initialRequestLine) {
        this.path = initialRequestLine[REQUEST_PATH];
    }

    public boolean parse() throws IOException {
        String initialLine = in.readLine();
        System.out.println("initialLine " + initialLine);
        if (initialLine == null || initialLine.length() == 0) {
            System.out.println("invalid initial line");
            return false;
        }

        getRequestMethod(initialLine.split(" ",3));
        getRequestPath(initialLine.split(" ",3));

        String header = in.readLine();
        while (header != null && header.length() > 0 ) {
            int separatorIndex = header.indexOf(":");
            if (separatorIndex == -1) { //there is no separator means invalid request format
                return false;
            }

            header = in.readLine();
            System.out.println("header " + header);
        }
        return true;
    }
}
