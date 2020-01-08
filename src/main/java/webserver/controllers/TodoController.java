package webserver.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mustachejava.Mustache;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import webserver.Callback;
import webserver.MustacheUtil;
import webserver.JsonTodos;
import webserver.request.HTTP;
import webserver.request.Parser;
import webserver.request.Request;
import webserver.Response;
import webserver.ResponseBody;

import webserver.models.Todo;
import webserver.models.Todos;

@SuppressWarnings("unchecked")
public class TodoController {
    private static final int ID_PARAM = 2;
    private static final String PATH_SEPARATOR = "/";
    private JsonTodos jsonTodos;
    private Todos todos;

    public TodoController(JsonTodos jsonTodos) {
        this.jsonTodos = jsonTodos;
        this.todos = new Todos(jsonTodos);
    }

    public Callback<Request, String> getTodoNotFound = (request) -> {
        return ResponseBody.getHtml("/404.html");
    };

    public Callback<Request, String> headTodoList= (request) -> {
        return headResponseBuilder(200);
    };

    public Callback<Request, String> headTodoDetail = (request) -> {
        return headResponseBuilder(200);
    };

    public Callback<Request, String> getTodoList = (request) -> {
        List<Todo> allTodos = todos.getAllTodos();
        Map<String, Object> context = new HashMap<>();
        Mustache todosTemplate = MustacheUtil.getTemplate("todos.mustache");

        context.put("todos", allTodos);
        return getResponseBuilder(200, MustacheUtil.executeTemplate(todosTemplate, context));

    };

    public Callback<Request, String> getTodoDetail = (request) -> {
        final int id = getTodoIdFromPath(request.getPath());
        String html = "";
        int responseCode = 0;

        Mustache template = MustacheUtil.getTemplate("todo-item.mustache");
        JSONObject todoItem = jsonTodos.getTodoById(id);
        
        if (todoItem == null) {
            html = getTodoNotFound.apply(request);
            responseCode = HTTP.STATUS_400;
        } else {
            Todo todo = Todo.fromJson(todoItem);
            html = MustacheUtil.executeTemplate(template, todo);
            responseCode = HTTP.STATUS_200;
        }

        return getResponseBuilder(responseCode, html);
    };

    public Callback<Request, String> getFilteredList = (request) -> {
        String query = request.getQuery().get("filter");
        List<Todo> filteredTodos = new ArrayList<>();
        Map<String, Object> context = new HashMap<>();
        Mustache todosTemplate = MustacheUtil.getTemplate("todos-filtered.mustache");
        JSONArray todosData = jsonTodos.getAllTodos();

        for (JSONObject todoObj : (Iterable<JSONObject>) todosData) {
            Todo todo = Todo.fromJson(todoObj);
            if ((todo.getTitle().contains(query)) || todo.getText().contains(query)) {
                filteredTodos.add(todo);
            }
        }

        context.put("todos", filteredTodos);
        return getResponseBuilder(200, MustacheUtil.executeTemplate(todosTemplate, context));
    };

    public Callback<Request, String> newTodo = (request) -> {
        String contentType = request.getHeaders().get(HTTP.CONTENT_TYPE);
        String body = request.getBody();
        if (contentType.equals("application/x-www-form-urlencoded")) {
            if (body.contains(" ")) { return postResponseBuilder(400, "/todo");}

            HashMap<String, String> parsedBody = Parser.parseTodoFormBody(body);
            int id = jsonTodos.getAllTodos().size() + 1;
            
            JSONObject newTodo = Todo.toJson(
                                        parsedBody.get("title"), 
                                        parsedBody.get("text"), 
                                        id, 
                                        false);
        
            jsonTodos.addTodo(newTodo); //need to handle if add is not successful
    
            return postResponseBuilder(201, "/todo/"+ id);
        } else {
            return postResponseBuilder(415, "/todo");
        }
    };

    public Callback<Request, String> updateTodoDetail = (request) -> {
        String path = request.getPath();
        int id = getTodoIdFromPath(path);
        JSONObject todoItem = jsonTodos.getTodoById(id);
        boolean isDone = Boolean.parseBoolean(todoItem.get("done").toString());
        jsonTodos.updateTodo(id, !isDone);

        return postResponseBuilder(303, "/todo/" + id);
    };
 
    private int getTodoIdFromPath(String path) {
        String[] pathSections = path.split(PATH_SEPARATOR);
        int id = Integer.parseInt(pathSections[ID_PARAM]);
        return id;
    }

    private String getResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
            .withHeader(HTTP.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP.CONTENT_LENGTH, Integer.toString(body.length()))
            .withBody(body)
            .build();
    }

    private String postResponseBuilder(int statusCode, String redirectPath) {
        return new Response.Builder(statusCode)
            .withHeader(HTTP.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP.CONTENT_LENGTH, "0")
            .withHeader(HTTP.LOCATION, redirectPath)
            .build();
    }

    private String headResponseBuilder(int statusCode) {
        return new Response.Builder(statusCode)
            .withHeader(HTTP.CONTENT_TYPE, "text/html; charset=utf-8")
            .build();
    }
}
