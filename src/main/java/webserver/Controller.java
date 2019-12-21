package webserver;

import java.io.IOException;

public class Controller {
    public String getIndex() throws IOException {
        String body = ResponseBody.getHtml("/index.html");
        return getResponseBuilder(200, body);
    }

    public String getHealthCheck() throws IOException {
        String body = ResponseBody.getHtml("/public/health-check.html");
        return getResponseBuilder(200, body);
    }

    public String getNotFound() throws IOException {
        String body = ResponseBody.getHtml("/404.html");
        return getResponseBuilder(404, body);
    }

    public String getListingList() throws IOException {
        String body = ResponseBody.getHtml("/public/todo.html");
        return getResponseBuilder(200, body);
    }

    public String getListingDetail(int id) throws IOException {
        String body = ResponseBody.getHtml("/public/" + id +".html");
        return getResponseBuilder(200, body);
    }

    public String getDirectoryFile(String path) throws IOException {
        int responseCode = 200;
        String body = ResponseBody.getHtml(path);

        return getResponseBuilder(responseCode, body);
    }

    public String headIndex() throws IOException {
        String body = ResponseBody.getHtml("/index.html");
        return headResponseBuilder(200, body);
    }

    public String headHealthCheck() throws IOException {
        String body = ResponseBody.getHtml("/public/health-check.html");
        return headResponseBuilder(200, body);
    }

    public String headNotFound() throws IOException {
        String body = ResponseBody.getHtml("/404.html");
        return headResponseBuilder(404, body);
    }

    public String headListingList() throws IOException {
        String body = ResponseBody.getHtml("/public/todo.html");
        return headResponseBuilder(200, body);
    }

    public String headListingDetail(int id) throws IOException {
        String body = ResponseBody.getHtml("/public/" + id +".html");
        return headResponseBuilder(200, body);
    }


    private String getResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
                .withContentType("Content-Type: text/html; charset=utf-8")
                .withBodyLength(body.length())
                .withBody(body)
                .build();
    }

    private String headResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
                .withContentType("Content-Type: text/html; charset=utf-8")
                .withBodyLength(body.length())
                .build();
    }
}
