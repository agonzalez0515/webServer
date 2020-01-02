package webserver;

import java.util.Map;

public class Response {
    private static final String CRLF = "\r\n";
    private static final Map<Integer, String> responseStatus = buildResponseCodes();
    private final int statusCode;
    private final String body;
    private final String contentType;
    private final int bodyLength;

    public static class Builder {
        private final int statusCode;
        private String body = "";
        private String contentType;
        private int bodyLength;

        public Builder(int statusCode) {
            this.statusCode = statusCode;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder withBodyLength(int length) {
            this.bodyLength = length;
            return this;
        }

        public String build() {
            Response res = new Response(this);
            return res.toResponseString();
        }
    }

    private Response(Builder builder) {
        this.statusCode = builder.statusCode;
        this.contentType = builder.contentType;
        this.body = builder.body;
        this.bodyLength = builder.bodyLength;
    }

    private static Map<Integer, String> buildResponseCodes() {
        var responses = Map.of(200, "OK", 404, "Not Found");
        return responses;
    }

    private String createInitialResponseLine(int statusCode) {
        String initialResponseLine = "HTTP/1.1 " + statusCode + " " + responseStatus.get(statusCode);
        return initialResponseLine;
    }

    public String toResponseString() {
        String initialLine = createInitialResponseLine(statusCode);
        String contentLengthHeader = "Content-Length: " + bodyLength;
        String responseString = initialLine + CRLF + contentType + CRLF + contentLengthHeader + CRLF + CRLF + body
                + "\n";

        return responseString;
    }
}
