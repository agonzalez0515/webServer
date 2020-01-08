package webserver.models;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import webserver.JsonTodos;

@SuppressWarnings("unchecked")
public class Todos {
    private JsonTodos json;

    public Todos(JsonTodos json) {
        this.json = json;
    }

    public List<Todo> getAllTodos() {
        List<Todo> allTodos = new ArrayList<>();
        JSONArray todosData = json.getAllTodos();

        for (JSONObject todoObj : (Iterable<JSONObject>) todosData) {
            allTodos.add(Todo.fromJson(todoObj));
        }
        return allTodos;
    }
}
