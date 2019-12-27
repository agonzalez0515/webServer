package webserver.request;

import java.util.HashMap;

public class Request {
    private  HashMap<String, String> headers = new HashMap<String, String>();
    private String method;
    private String path;
    private String body;

    public static class Builder {
        private String method;
        private String path;
        private HashMap<String, String> headers;
        private String body;

        public Builder(String method, String path) {
            this.method = method;
            this.path = path;
        }

        public Builder withHeaders(HashMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Request build() {
            Request request = new Request(this);
            return request;
        }
    }

    private Request(Builder builder) {
        this.method = builder.method;
        this.path = builder.path;
        this.body = builder.body;
        this.headers = builder.headers;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getBody() {
        return this.body;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

}
