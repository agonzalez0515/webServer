package webserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class Response {
    public Map<String, String> headers = new HashMap<String, String>();
    private Hashtable responseStatus = new Hashtable();
    private PrintWriter out;
    private String method;
    private String path;
    private int statusCode;

    public Response(PrintWriter out, String method, String path) {
        this.out = out;
        this.method = method;
        this.path = path;
    }

    public void createHeader(String headerName, String headerValue) {
        this.headers.put(headerName, headerValue);
    }

    public String getHTML(String path) {
        try {
            if (path.equals("/")) {
                Path filePath = FileSystems.getDefault().getPath("/index.html".substring(1));
                FileInputStream file = new FileInputStream(filePath.toString());
                StringBuffer buf = new StringBuffer();
                int c;
                while ((c = file.read()) != -1) {
                    buf.append((char) c);
                }
                this.statusCode = 200;
                return buf.toString();
            } else {
                Path filePath = FileSystems.getDefault().getPath(path.substring(1));
                FileInputStream file = new FileInputStream(filePath.toString());
                StringBuffer buf = new StringBuffer();
                int c;
                while ((c = file.read()) != -1) {
                    buf.append((char) c);
                }
                this.statusCode = 200;
                return buf.toString();
            }

        } catch (FileNotFoundException e) {
            this.statusCode = 404;
            return "File not found.";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ""; //how to handle this?
    }

    public String setInitialResponseLine(int statusCode) {
        String initialResponseLine = "";
        initialResponseLine = "HTTP/1.1 " + statusCode + " " + responseStatus(statusCode);
        return initialResponseLine;
    }

    private Object responseStatus(int statusCode) {
        responseStatus.put(200, "OK"); //refactor to be SOLID
        responseStatus.put(404, "Not Found");
        return responseStatus.get(statusCode);
    }

    public void send() throws IOException {
        String dataToSend = getHTML(path);
        String initialResponseLine = setInitialResponseLine(statusCode);
        out.println(initialResponseLine);
        out.println("Content-Length: " + dataToSend.length());
        out.println("Content-Type: text/html");
        out.println("\r\n");
        out.println(dataToSend);
    }
}
