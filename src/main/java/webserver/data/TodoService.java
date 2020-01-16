package webserver.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import webserver.models.Todo;

@SuppressWarnings("unchecked")
public class TodoService implements Todos {
    private JsonClient json;

    public TodoService(String directory) {
        this.json = new JsonClient(directory);
    }

    public List<Todo> getTodos() {
        List<Todo> allTodos = new ArrayList<>();
        JSONArray todosData = json.getAllItems();

        for (JSONObject todoObj : (Iterable<JSONObject>) todosData) {
            Todo todo = Todo.fromJson(todoObj);
            allTodos.add(todo);
        };
       return allTodos;
    }

    public void addTodo(Map<String, String> body, int id) {
        String title = body.get("title");
        String text = body.get("text");
        Todo newTodo = new Todo(title, text, id, false);
        JSONObject todoJson = Todo.toJson(newTodo);
        
        json.addItem(todoJson);
    }

    public void updateTodo(Todo todo) {
        int id = todo.getId();
        JSONObject updatedTodo = Todo.toJson(todo);
        json.updateItemById(id, updatedTodo);
    }

    public void deleteTodo(int id) {
        json.deleteItemById(id);
    }
}
