package webserver.routes;

import webserver.JsonTodos;
import webserver.Router;
import webserver.controllers.TodoController;

public class TodoRoutes {
    TodoController todoController;
    
    public TodoRoutes(String directory) {
        this.todoController = new TodoController(new JsonTodos("/" + directory + "/todos.json"));
    }

    public void build(Router router) {
        router.get("/todo", todoController.getTodoList);
        router.get("/todo/[0-9]+", todoController.getTodoDetail);
        router.get("/todo?(.*)", todoController.getFilteredList);

        router.head("/todo", todoController.headTodoList);
        router.head("/todo/[0-9]+", todoController.headTodoDetail);

        router.post("/todo", todoController.newTodo);
        router.post("/todo/[0-9]+/toggle", todoController.updateTodoDetail);
    }
}
