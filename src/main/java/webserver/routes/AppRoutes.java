package webserver.routes;

import webserver.Router;
import webserver.controllers.AppController;

public class AppRoutes {
  public void build(Router router) {
    router.get("/", AppController.getIndex);
    router.get("/health-check", AppController.getHealthCheck);
    router.get("/.+\\.css$", AppController.getResources);

    router.head("/health-check", AppController.headHealthCheck);
  }
}
