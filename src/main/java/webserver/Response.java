package webserver;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

public class Response {
    private static final String CRLF = "\r\n";
    private static final Map<Integer, String> responseStatus = buildResponseCodes();
    private PrintWriter out;
    private int statusCode;
    private String body = "";
    private String requestMethod;

    public Response(PrintWriter out, String requestMethod, String htmlToSend, int statusCode) {
        this.out = out;
        this.requestMethod = requestMethod;
        this.body = htmlToSend;
        this.statusCode = statusCode;
    }

    private static Map<Integer, String> buildResponseCodes() {
        var responses = Map.of(
            200, "OK",
            404, "Not Found"
        );
        return responses;
    }

    public void send() throws IOException {
        out.print(createInitialResponseLine(statusCode));
        out.print(CRLF);
        out.print("Content-Length: " + body.length());
        out.print(CRLF);
        out.print("Content-Type: text/html; charset=utf˝-8");
        out.print(CRLF);
        out.print(CRLF);

        if (requestMethod.equals("GET")){
            out.println(body);
        }
    }

    private String createInitialResponseLine(int statusCode) {
        String initialResponseLine = "HTTP/1.1 " + statusCode + " " + responseStatus.get(statusCode);
        return initialResponseLine;
    }
}
