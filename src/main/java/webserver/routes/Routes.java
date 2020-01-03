package webserver.routes;

import webserver.Router;

public class Routes {
    private TodoRoutes todoRoutes;
    private AppRoutes appRoutes;
    private Router router;

    public Routes(Router router) {
        this.router = router;
        this.todoRoutes = new TodoRoutes();
        this.appRoutes = new AppRoutes();
    }

    public void setupRoutes() {
        todoRoutes.build(router);
        appRoutes.build(router);
    }
}  
