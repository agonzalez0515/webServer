package webserver.routes;

import webserver.Router;
import webserver.controllers.TodoController;
import webserver.data.TodoService;

public class TodoRoutes {
    TodoController todoController;
    
    public TodoRoutes(String directory) {
        this.todoController = new TodoController(new TodoService(directory));
    }

    public void build(Router router) {
        router.get("/todo", todoController.getTodoList);
        router.get("/todo/[0-9]+", todoController.getTodoDetail);
        router.get("/todo/[0-9]+/edit", todoController.getTodoDetailEdit);
        router.get("/todo?(.*)", todoController.getFilteredList);

        router.head("/todo", todoController.headTodoList);
        router.head("/todo/[0-9]+", todoController.headTodoDetail);

        router.post("/todo", todoController.newTodo);
        router.post("/todo/[0-9]+/toggle", todoController.updateTodoDone);

        router.put("/todo/[0-9]+", todoController.editTodo);
    }
}
