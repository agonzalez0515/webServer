package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webserver.controllers.AppController;
import webserver.request.Request;
import webserver.request.HTTP_HEADERS;;

public class Router {
    private Map<String, Callback<Request, String>> GETRoutes = new HashMap<>();
    private Map<String, Callback<Request, String>> HEADRoutes = new HashMap<>();
    private Map<String, Callback<Request, String>> POSTRoutes = new HashMap<>();

    public void get(String route, Callback<Request, String> controller) {
        GETRoutes.put(route, controller);
    }
    
    public void head(String route, Callback<Request, String> controller) {
        HEADRoutes.put(route, controller);
    }

    public void post(String route, Callback<Request, String> controller) {
        POSTRoutes.put(route, controller);
    }
    
    public String route(Request request) {
        Callback<Request, String> controller = findController(request);
        return controller.apply(request);
    }

    private Callback<Request, String> findController(Request request) {
        String requestPath = request.getPath();
        String requestMethod = request.getMethod();
        Map<String, Callback<Request, String>> routes = getMethodRoutes(requestMethod);

        assert routes != null;
        Optional<Callback<Request, String>> controller = routes.entrySet()
                                                  .stream()
                                                  .filter(entry -> routeMatcher(requestPath, entry.getKey()))
                                                  .map(Map.Entry::getValue)
                                                  .findFirst();
        if (controller.isPresent()) {
            return controller.get();
        } else {
              return AppController.getNotFound;
        }
    }

    private Map<String, Callback<Request, String>> getMethodRoutes(String method) {
        switch(method) {
            case HTTP_HEADERS.GET:
                return this.GETRoutes;
            case HTTP_HEADERS.HEAD:
                return this.HEADRoutes;
            case HTTP_HEADERS.POST:
                return this.POSTRoutes;
        }
        return null;
    }

    private Boolean routeMatcher(String requestPath, String route) {
        Pattern pattern = Pattern.compile(route);
        Matcher matcher = pattern.matcher(requestPath);
        return matcher.matches();
    }
}
