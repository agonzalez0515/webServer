package webserver.routes;

import webserver.Router;
import webserver.controllers.AppController;

public class AppRoutes {
  public void build(Router router) {
    router.get("/", AppController.getIndex);
    router.get("/health-check", AppController.getHealthCheck);
    router.head("/health-check", AppController.headHealthCheck);
  }
}