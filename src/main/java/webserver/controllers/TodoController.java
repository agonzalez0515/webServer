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

@SuppressWarnings("unchecked")
public class TodoController {
    private static final int ID_PARAM = 2;
    private static final String PATH_SEPARATOR = "/";
    private JsonTodos jsonTodos;

    public TodoController(JsonTodos jsonTodos) {
        this.jsonTodos = jsonTodos;
    }

    public Callback<Request, String> getTodoNotFound = (request) -> {
        return ResponseBody.getHtml("/404.html");
    };

    public Callback<Request, String> headTodoList= (request) -> {
        return headResponseBuilder(200);
    };

    public Callback<Request, String> getTodoList = (request) -> {
        List<Todo> allTodos = new ArrayList<>();
        Map<String, Object> context = new HashMap<>();
        Mustache todosTemplate = MustacheUtil.getTemplate("todos.mustache");
        JSONArray todosData = jsonTodos.getAllTodos();

        for (JSONObject todoObj : (Iterable<JSONObject>) todosData) {
            allTodos.add(Todo.fromJson(todoObj));
        }

        context.put("todos", allTodos);
        return getResponseBuilder(200, MustacheUtil.executeTemplate(todosTemplate, context));

    };

    public Callback<Request, String> getTodoDetail = (request) -> {
        String html = "";
        int responseCode = 0;
        String path = request.getPath();
        String requestMethod = request.getMethod();

        if (requestMethod.equals(HTTP.HEAD)) { return headResponseBuilder(200);}

        int id = getTodoIdFromPath(path);

        Mustache template = MustacheUtil.getTemplate("todo-item.mustache");
        JSONObject todoItem = jsonTodos.getTodoById(id);
        if (todoItem == null) {
            html = getTodoNotFound.apply(request);
            responseCode = 404;

        } else {
            Todo todo = Todo.fromJson(todoItem);
            html = MustacheUtil.executeTemplate(template, todo);
            responseCode = 200;
        }

        return getResponseBuilder(responseCode, html);
    };

    public Callback<Request, String> newTodo = (request) -> {
        String contentType = request.getHeaders().get(HTTP.CONTENT_TYPE);
        if(contentType.equals("text/xml; charset=utf-8")) {
            return postResponseBuilder(415, "/todo");
        }

        HashMap<String, String> parsedBody = Parser.parseRequestBody(request.getBody());
        int id = jsonTodos.getAllTodos().size() + 1;
        
        JSONObject newTodo = Todo.toJson(
                                    parsedBody.get("title"), 
                                    parsedBody.get("text"), 
                                    id, 
                                    false);
 
        jsonTodos.addTodo(newTodo); //need to handle if add is not successful

        return postResponseBuilder(201, "/todo/"+ id);
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
