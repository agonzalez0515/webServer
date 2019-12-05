package webserver;

import java.io.IOException;

public class Controller {
    public String index() throws IOException {
        return ResponseBody.getHtml("/index.html");
    }

    public String healthCheck() throws IOException {
        return ResponseBody.getHtml("/public/health-check.html");
    }

    public String listingList() throws IOException {
        return ResponseBody.getHtml("/public/todo.html");
    }

    public String listingDetail(int id) throws IOException {
        return ResponseBody.getHtml("/public/" + id +".html");
    }
}