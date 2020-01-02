package webserver;

import java.io.IOException;

public class Router {
    private final Routes routes;

    public Router(Routes routes) {
        this.routes = routes;
    }

    public String route(String path, String method) throws IOException {
        String responseToSend = "";

        switch (method.toLowerCase()) {
            case "get":
                responseToSend = routes.get(path);
                break;
            case "head":
                responseToSend = routes.head(path);
                break;
        }
        return responseToSend;
    }
}
