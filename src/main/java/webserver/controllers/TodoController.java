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
import webserver.request.Request;
import webserver.Response;
import webserver.ResponseBody;
import webserver.Todo;

public class TodoController {
    private static final String EMPTY_SPACE = " ";
    private static final String FIELD_NAME_VALUE_SEPARATOR = "=";
    private static final String FIELDS_SEPARATOR = "&";
    private static final String PATH_SEPARATOR = "/";
    private static final String SPACE_CHAR = "\\+";
    private static JsonTodos jsonTodos = new JsonTodos("/public/todos.json");

    public static Callback<Request, String> getTodoNotFound = (request) -> {
        return ResponseBody.getHtml("/404.html");
    };

    @SuppressWarnings("unchecked")
    public static Callback<Request, String> getTodoList = (request) -> {
        String requestMethod = request.getMethod().toLowerCase();

        if (requestMethod.equals("head")) {
            return headResponseBuilder(200);
        }

        List<Todo> todos = new ArrayList<>();
        Map<String, Object> context = new HashMap<>();

        Mustache todosTemplate = MustacheUtil.getTemplate("todos.mustache");

        JSONArray todosData = jsonTodos.getAllTodos();

        for (JSONObject todoObj : (Iterable<JSONObject>) todosData) {
            String title = getProperty(todoObj, "title");
            String text = getProperty(todoObj, "text");
            int id = Integer.parseInt(getProperty(todoObj, "id"));

            Todo td = new Todo(title, text, id);

            todos.add(td);
        }

        context.put("todos", todos);
        return getResponseBuilder(200, MustacheUtil.executeTemplate(todosTemplate, context));

    };

    public static Callback<Request, String> getTodoDetail = (request) -> {
        String html = "";
        int responseCode = 0;
        String path = request.getPath();
        String requestMethod = request.getMethod();

        if (requestMethod.equals("HEAD")) { return headResponseBuilder(200);}

        int id = getTodoIdFromPath(path);

        Mustache template = MustacheUtil.getTemplate("todo-item.mustache");
        JSONObject todoItem = jsonTodos.getTodoById(id);

        if (todoItem == null) {
            html = getTodoNotFound.apply(request);
            responseCode = 404;

        } else {
            
            String title = getProperty(todoItem, "title");
            String text = getProperty(todoItem, "text");
            int todoId = Integer.parseInt(getProperty(todoItem, "id"));

            Todo td = new Todo(title, text, todoId);
            html = MustacheUtil.executeTemplate(template, td);
            responseCode = 200;
        }

        return getResponseBuilder(responseCode, html);
    };

    private static int getTodoIdFromPath(String path) {
        String[] pathSections = path.split(PATH_SEPARATOR);
        int id = Integer.parseInt(pathSections[2]);
        return id;
    }

    @SuppressWarnings("unchecked")
    public static Callback<Request, String> newTodo = (request) -> {
        String body = request.getBody();
        HashMap<String, String> parsedBody = parseRequestBody(body);
        JSONObject newTodo = new JSONObject();
        int id = jsonTodos.getAllTodos().size() + 1;

        newTodo.put("id", id);
        newTodo.put("title", parsedBody.get("title"));
        newTodo.put("text", parsedBody.get("text"));
        jsonTodos.addTodo(newTodo); //need to handle if add is not successful

        return postResponseBuilder(303, "/todo/"+ id);
    };

    private static String getProperty(JSONObject todoObj, String keyName) {
        return todoObj.get(keyName).toString();
    }

    private static HashMap<String, String> parseRequestBody(final String body) {
        final HashMap<String, String> submittedFields = new HashMap<String, String>();
        final String[] parsedBody = body.split(FIELDS_SEPARATOR);

        for (final String fieldText : parsedBody) {
            final int separatorIndex = fieldText.indexOf(FIELD_NAME_VALUE_SEPARATOR);
            final String fieldName = parsedNameField(fieldText, separatorIndex);
            final String fieldValue = parsedValueField(fieldText, separatorIndex);
            submittedFields.put(fieldName, fieldValue);
        }

        return submittedFields;
    }

    private static String parsedNameField(final String fieldText, final int separatorIndex) {
        return fieldText.substring(0, separatorIndex).replaceAll(SPACE_CHAR, EMPTY_SPACE);
    }

    private static String parsedValueField(final String fieldText, final int separatorIndex) {
        return fieldText.substring(separatorIndex + 1).replaceAll(SPACE_CHAR, EMPTY_SPACE);
    }

    private static String getResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
        .withHeader("Content-Type", "text/html; charset=utf-8")
        .withHeader("Content-Length", Integer.toString(body.length()))
        .withBody(body)
        .build();
    }

    private static String postResponseBuilder(int statusCode, String redirectPath) {
        return new Response.Builder(statusCode)
        .withHeader("Content-Type", "text/html; charset=utf-8")
        .withHeader("Content-Length", "0")
        .withHeader("Location", redirectPath)
        .build();
    }

    private static String headResponseBuilder(int statusCode) {
        return new Response.Builder(statusCode).withHeader("Content-Type", "text/html; charset=utf-8").build();
    }
}
