package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Response {
    private static final String CRLF = "\r\n";
    private static final Map<Integer, String> responseStatus = buildResponseCodes();
    private final int statusCode;
    private final String body;
    private final HashMap<String, String> headers;

    public static class Builder {
        private final int statusCode;
        private String body = "";
        private HashMap<String, String> headers = new HashMap<String, String>();

        public Builder(int statusCode) {
            this.statusCode = statusCode;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withHeader(String headerName, String headerValue) {
            headers.put(headerName, headerValue);
            return this;
        }

        public String build() {
            Response res = new Response(this);
            return res.toResponseString();
        }
    }

    private Response(Builder builder) {
        this.statusCode = builder.statusCode;
        this.body = builder.body;
        this.headers = builder.headers;
    }

    private static Map<Integer, String> buildResponseCodes() {
        var responses = Map.of(200, "OK", 201, "Created", 404, "Not Found");
        return responses;
    }

    private String createInitialResponseLine(int statusCode) {
        String initialResponseLine = "HTTP/1.1 " + statusCode + " " + responseStatus.get(statusCode);
        return initialResponseLine;
    }

    public String toResponseString() {
        String initialLine = createInitialResponseLine(statusCode);
        String allHeaders = convertHeaderMapToString(headers);
        String responseString = initialLine + CRLF + allHeaders + CRLF + CRLF + body + "\n";
      
        return responseString;
    }

    private String convertHeaderMapToString(HashMap<String, String> headers) {
        String mapAsString = headers.keySet()
                                    .stream()
                                    .map(key -> key + ": " + headers.get(key) + CRLF)
                                    .collect(Collectors.joining(""));
        return mapAsString;
    }
}
