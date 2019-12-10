package webserver;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

public class Response {
   private static final String CRLF = "\r\n";
   private static final Map<Integer, String> responseStatus = buildResponseCodes();
   private final int statusCode;
   private final String body;
   private final String contentType;
   private final PrintWriter out;

   public static class Builder {
        private final int statusCode;
        private final PrintWriter out;
        private String body;
        private String contentType;

        public Builder(PrintWriter out, int statusCode) {
            this.statusCode = statusCode;
            this.out = out;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Response build() {
            return new Response(out, this);
        }
   }

   private Response(PrintWriter out, Builder builder) {
       this.statusCode = builder.statusCode;
       this.contentType = builder.contentType;
       this.body = builder.body;
       this.out = out;
   }

   private static Map<Integer, String> buildResponseCodes() {
       var responses = Map.of(
           200, "OK",
           404, "Not Found"
       );
       return responses;
   }

   private String createInitialResponseLine(int statusCode) {
       String initialResponseLine = "HTTP/1.1 " + statusCode + " " + responseStatus.get(statusCode);
       return initialResponseLine;
   }

   public void send() throws IOException {
       out.print(createInitialResponseLine(statusCode));
       out.print(CRLF);
       out.print(contentType);
       out.print(CRLF);
       out.print("Content-Length: " + body.length());
       out.print(CRLF);
       out.print(CRLF);
       out.println(body);
   }
}
