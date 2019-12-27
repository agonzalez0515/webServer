package webserver.routes;

import webserver.Router;
import webserver.controllers.TodoController;

public class TodoRoutes {

  public void addRoutes(Router router) {
    router.get("/todo", TodoController.getTodoList);
    router.get("/todo/[0-9]+", TodoController.getTodoDetail);
    router.post("/todo", TodoController.newTodo);
  }

}
