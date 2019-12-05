package webserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_PATH = 1;
    private BufferedReader in;
    private String method;
    private String path;

    public Request (final BufferedReader in) {
        this.in = in;
    }

    public String getRequestMethod() {
        return this.method;
    }

    public String getRequestPath() {
        return this.path;
    }

    public boolean parse() throws IOException {
        final String initialLine = in.readLine();
        if (initialLine == null || initialLine.length() == 0) { // there is no initial line means invalid request format
            return false;
        }

        setRequestMethod(initialLine.split(" ", 3));
        setRequestPath(initialLine.split(" ", 3));

        String header = in.readLine();
        while (header != null && header.length() > 0) {
            final int separatorIndex = header.indexOf(":");
            if (separatorIndex == -1) { //there is no separator means invalid request format
                return false;
            }

            header = in.readLine();
        }
        return true;
    }

    private void setRequestMethod(final String[] initialRequestLine) {
        this.method = initialRequestLine[REQUEST_METHOD];
    }

    private void setRequestPath(final String[] initialRequestLine) {
        this.path = initialRequestLine[REQUEST_PATH];
    }
}
