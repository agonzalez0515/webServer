package webserver.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.mustachejava.Mustache;

import webserver.Callback;
import webserver.MustacheUtil;
import webserver.data.TodoService;
import webserver.data.TodosHelper;
import webserver.request.HTTP_HEADERS;
import webserver.request.Parser;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseBody;
import webserver.models.Todo;

public class TodoController {
    private static final int ID_PARAM = 2;
    private static final String PATH_SEPARATOR = "/";
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    public Callback<Request, String> getTodoNotFound = (request) -> {
        return ResponseBody.getHtml("/404.html");
    };

    public Callback<Request, String> headTodoList= (request) -> {
        return new Response.Builder(HTTP_HEADERS.STATUS_200)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, "0")
            .build();
    };

    public Callback<Request, String> headTodoDetail = (request) -> {
        return new Response.Builder(HTTP_HEADERS.STATUS_200)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, "0")
            .build();
    };

    public Callback<Request, String> getTodoList = (request) -> {
        List<Todo> allTodos = todoService.getTodos();
        Map<String, Object> context = new HashMap<>();
        Mustache todosTemplate = MustacheUtil.getTemplate("todos.mustache");

        context.put("todos", allTodos);
        String html = MustacheUtil.executeTemplate(todosTemplate, context);

        return new Response.Builder(HTTP_HEADERS.STATUS_200)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, getContentLength(html))
            .withBody(html)
            .build();
    };

    public Callback<Request, String> getTodoDetail = (request) -> {
        final int id = getTodoIdFromPath(request.getPath());
        String html = "";
        int responseCode = 0;

        Mustache template = MustacheUtil.getTemplate("todo-item.mustache");
        List<Todo> allTodos = todoService.getTodos();
        Todo todo = TodosHelper.getTodo(id, allTodos);

        if (todo == null) {
            html = getTodoNotFound.apply(request);
            responseCode = HTTP_HEADERS.STATUS_400;
        } else {
            html = MustacheUtil.executeTemplate(template, todo);
            responseCode = HTTP_HEADERS.STATUS_200;
        }

        return new Response.Builder(responseCode)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, getContentLength(html))
            .withBody(html)
            .build();
    };

    public Callback<Request, String> getFilteredList = (request) -> {
        String query = request.getQuery().get("filter");
        List<Todo> filteredTodos = TodosHelper.getFilteredTodos(query, todoService.getTodos());
        Map<String, Object> context = new HashMap<>();
        Mustache template = MustacheUtil.getTemplate("todos-filtered.mustache");
        context.put("todos", filteredTodos);
        String html = MustacheUtil.executeTemplate(template, context);

        return new Response.Builder(HTTP_HEADERS.STATUS_200)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, getContentLength(html))
            .withBody(html)
            .build();
    };

    public Callback<Request, String> getTodoDetailEdit = (request) -> {
        final int id = getTodoIdFromPath(request.getPath());
        String html = "";
        int responseCode = 0;

        Mustache template = MustacheUtil.getTemplate("todo-item-edit.mustache");
        List<Todo> allTodos = todoService.getTodos();
        Todo todo = TodosHelper.getTodo(id, allTodos);

        if (todo == null) {
            html = getTodoNotFound.apply(request);
            responseCode = HTTP_HEADERS.STATUS_400;
        } else {
            html = MustacheUtil.executeTemplate(template, todo);
            responseCode = HTTP_HEADERS.STATUS_200;
        }

        return new Response.Builder(responseCode)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, getContentLength(html))
            .withBody(html)
            .build();
    };

    public Callback<Request, String> editTodo = (request) -> {
        int id = getTodoIdFromPath(request.getPath());
        String contentType = request.getHeaders().get(HTTP_HEADERS.CONTENT_TYPE);
        String body = request.getBody();
        List<Todo> allTodos = todoService.getTodos();
        Todo todo = TodosHelper.getTodo(id, allTodos);
 
        if (contentType.equals("application/x-www-form-urlencoded")) {

            if (body.equals("") || body.contains(" ")) { 
                return new Response.Builder(HTTP_HEADERS.STATUS_400)
                .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
                .build();                    
            }

            Map<String, String> parsedBody = Parser.parseTodoFormBody(body);
            todo.setTitle(parsedBody.get("title"));
            todo.setText(parsedBody.get("text"));
            todoService.updateTodo(todo); //TODO handle if update is not successful
            Mustache template = MustacheUtil.getTemplate("todo-item.mustache");
            String html = MustacheUtil.executeTemplate(template, todo);
    
            return new Response.Builder(HTTP_HEADERS.STATUS_200)
                .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
                .withHeader(HTTP_HEADERS.CONTENT_LENGTH, getContentLength(html))
                .withHeader(HTTP_HEADERS.LOCATION, "/todo/" + id)
                .withBody(html)
                .build();
        } else {

            return new Response.Builder(HTTP_HEADERS.STATUS_415)
                .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
                .build();
        }
    };

    public Callback<Request, String> newTodo = (request) -> {
        String contentType = request.getHeaders().get(HTTP_HEADERS.CONTENT_TYPE);
        String body = request.getBody();
        int statusCode; 
        String redirectPath;

        if (contentType.equals("application/x-www-form-urlencoded")) {
            
            if (body.contains(" ")) { 
                return new Response.Builder(HTTP_HEADERS.STATUS_400)
                .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
                .withHeader(HTTP_HEADERS.CONTENT_LENGTH, "0")
                .withHeader(HTTP_HEADERS.LOCATION, "/todo")
                .build();                    
            }

            Map<String, String> parsedBody = Parser.parseTodoFormBody(body);
            int id = todoService.getTodos().size() + 1;
            todoService.addTodo(parsedBody, id); //TODO handle if add is not successful
    
            statusCode = HTTP_HEADERS.STATUS_201;
            redirectPath = "/todo/" + id;
        } else {
            statusCode = HTTP_HEADERS.STATUS_415;
            redirectPath = "/todo/";
        }

        return new Response.Builder(statusCode)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, "0")
            .withHeader(HTTP_HEADERS.LOCATION, redirectPath)
            .build();
    };

    public Callback<Request, String> updateTodoDone = (request) -> {
        int id = getTodoIdFromPath(request.getPath());
        List<Todo> allTodos = todoService.getTodos();
        Todo todo = TodosHelper.getTodo(id, allTodos);
        boolean isDone = todo.getDone();
        todo.setDone(!isDone);
        todoService.updateTodo(todo); //TODO handle if update is not successful

        return new Response.Builder(HTTP_HEADERS.STATUS_303)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, "0")
            .withHeader(HTTP_HEADERS.LOCATION, "/todo/" + id)
            .build();
    };

    public Callback<Request, String> deleteTodo = (request) -> {
        Integer id = getTodoIdFromPath(request.getPath());
        List<Integer> ids = todoService.getTodos().stream().map(todo -> todo.getId()).collect(Collectors.toList());

        if (id != null && ids.contains(id) ) {
            todoService.deleteTodo(id);
        }
        
        return new Response.Builder(HTTP_HEADERS.STATUS_204)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .build();
    };

    private String getContentLength(String body) {
        return Integer.toString(body.length());
    }
 
    private Integer getTodoIdFromPath(String path) {
        String[] pathSections = path.split(PATH_SEPARATOR);
        try {
            int id = Integer.parseInt(pathSections[ID_PARAM]);
            return id;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
