package webserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;


public class Response {
    private static final String CRLF = "\r\n";
    private static Map<Integer, String> responseStatus = buildResponseCodes();
    private PrintWriter out;
    private String path;
    private int statusCode;
    private String body;


    public Response(PrintWriter out, String path) {
        this.out = out;
        this.path = path;
    }

    private static Map<Integer, String> buildResponseCodes() {
        Map<Integer, String> responses = new HashMap<Integer, String>();
        responses.put(200, "OK");
        responses.put(404, "Not Found"); 
        return responses;
    }

    public void setupDataToBeSent() throws IOException {
        Path parsedPath = createFilePath(createPathString(path));
        setStatusCode(parsedPath);
        getHTML(parsedPath);
    }

    public void send() throws IOException {
        out.print(createInitialResponseLine(statusCode));
        out.print(CRLF);
        out.print("Content-Length: " + body.length());
        out.print(CRLF);
        out.print("Content-Type: text/html");
        out.print(CRLF);
        out.print(CRLF);
        out.println(body);
    }

    private String createPathString(String path) {
        if (path.equals("/")) {
            path = "/index.html";
        }

        if(!path.contains("html")) {
            path = path +".html";
        }
        return path;
    }

    private void setStatusCode(Path path) {
        if (Files.exists(path)) {
            this.statusCode = 200;
        } else {
            this.statusCode = 404;
        }
    }

    private void getHTML(Path parsedPath) throws IOException {
        try(FileInputStream file = getFile(parsedPath)) {
            String htmlString = createHTMLString(file);
            this.body = htmlString;
        }
    }

    private FileInputStream getFile(Path path) {
        try {
            FileInputStream file = new FileInputStream(path.toString());
            return file;
        } catch (FileNotFoundException e) {
            Path errorFile = createFilePath("/404.html");
            return getFile(errorFile);
        }
    }

    private Path createFilePath(String path) {
        Path filePath = FileSystems.getDefault().getPath(path.substring(1));
        return filePath;
    }

    private String createHTMLString(FileInputStream file) throws IOException {
        StringBuffer buf = new StringBuffer();

        int c;
        while ((c = file.read()) != -1) {
            buf.append((char) c);
        }
        return buf.toString();
    }

    private String createInitialResponseLine(int statusCode) {
        String initialResponseLine = "HTTP/1.1 " + statusCode + " " + responseStatus.get(statusCode);
        return initialResponseLine;
    }
}
