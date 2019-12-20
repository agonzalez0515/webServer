package webserver;

import java.io.IOException;
import java.util.Map;

public class Routes {
    private Map<String, String> getRoutes;
    private Map<String, String> headRoutes;
    private final Controller controller;
    private String directory;


    public Routes(Controller controller, String directory) {
        this.controller = controller;
        this.directory = directory;

        try {
            getRoutes = Map.of(
                "/", controller.getIndex(), 
                "/todo", controller.getListingList(),
                "/todo/1", controller.getListingDetail(1),
                "/todo/2", controller.getListingDetail(2),
                "/todo/3", controller.getListingDetail(3),
                "/health-check", controller.getHealthCheck()
                );

            headRoutes = Map.of(
                "/", controller.headIndex(), 
                "/todo", controller.headListingList(),
                "/todo/1", controller.headListingDetail(1),
                "/todo/2", controller.headListingDetail(2),
                "/todo/3", controller.headListingDetail(3),
                "/health-check", controller.headHealthCheck()
                );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String path) throws IOException {
        if (getRoutes.containsKey(path)) {
            final String response = getRoutes.get(path);
            return response != null ? response : controller.getNotFound();
        } else if (!getRoutes.containsKey(path) && !directory.equals("")) {
            return controller.getDirectoryFile(path);
        } else {
            return controller.getNotFound();
        }
    }

    public String head(String path) throws IOException {
        final String response = headRoutes.get(path);
        return response != null ? response : controller.headNotFound();
    }
}

//FIXME refactor all of it! 