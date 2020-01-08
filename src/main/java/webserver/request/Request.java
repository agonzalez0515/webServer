package webserver.request;

import java.util.Map;

public class Request {
    private Map<String, String> headers;
    private Map<String, String> query;
    private String method;
    private String path;
    private String body;
    
    public static class Builder {
        private Map<String, String> headers;
        private Map<String, String> query;
        private String method;
        private String path;
        private String body;

        public Builder(String method, String path) {
            this.method = method;
            this.path = path;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withQuery(Map<String, String> query) {
            this.query = query;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

    private Request(Builder builder) {
        this.method = builder.method;
        this.path = builder.path;
        this.body = builder.body;
        this.headers = builder.headers;
        this.query = builder.query;
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

    public Map<String, String> getQuery() {
        return this.query;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

}
