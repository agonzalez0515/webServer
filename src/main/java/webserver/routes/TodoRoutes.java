package webserver.routes;

import webserver.JsonTodos;
import webserver.Router;
import webserver.controllers.TodoController;

public class TodoRoutes {
    TodoController todoController;
    
    public TodoRoutes() {
        this.todoController = new TodoController(new JsonTodos("/public/todos.json"));
    }

    public void build(Router router) {
        router.get("/todo", todoController.getTodoList);
        router.get("/todo/[0-9]+", todoController.getTodoDetail);
        router.head("/todo", todoController.headTodoList);
        router.post("/todo", todoController.newTodo);
        router.post("/todo/[0-9]+/toggle", todoController.updateTodoDetail);
    }
}
