package webserver.data;

import java.util.List;
import java.util.Map;

import webserver.models.Todo;

interface Todos {
    public List<Todo> getTodos();

    public void addTodo(Map<String, String> body, int id);

    public void updateTodo(Todo todo);
}
