package webserver.routes;

import webserver.Router;

public class Routes {
    private TodoRoutes todoRoutes;
    private AppRoutes appRoutes;
    private Router router;

    public Routes(Router router, String directory) {
        this.router = router;
        this.todoRoutes = new TodoRoutes(directory);
        this.appRoutes = new AppRoutes();
    }

    public void setupRoutes() {
        todoRoutes.build(router);
        appRoutes.build(router);
    }
}  
