package webserver.controllers;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.containsString;

import webserver.request.Request;
import webserver.JsonTodos;


@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class TodoControllerTest {
    TodoController controller;

    @Mock
    Request request;

    @Mock
    JsonTodos jsonTodos;

    @Before
    public void init() {
        controller = new TodoController(jsonTodos);
    }

    @Test
    public void itReturnsAllTodoItems() throws IOException {
        JSONObject todos = new JSONObject();
        todos.put("title","hello");
        todos.put("text","bye");
        todos.put("id", 1);
        todos.put("done", false);
        JSONArray todosArray = new JSONArray();
        todosArray.add(todos);
        
        when(jsonTodos.getAllTodos()).thenReturn(todosArray);
        String htmlString = controller.getTodoList.apply(request);

        assertThat(htmlString, containsString("hello"));
    }

    @Test
    public void itReturnsOneTodoItemWithDetails() throws IOException {
        JSONObject todo = new JSONObject();
        todo.put("title","hello");
        todo.put("text","bye");
        todo.put("id", 1);
        todo.put("done", false);
        when(request.getPath()).thenReturn("/todo/1");
        when(request.getMethod()).thenReturn("GET");
        when(jsonTodos.getTodoById(1)).thenReturn(todo);
        String htmlString = controller.getTodoDetail.apply(request);

        assertThat(htmlString, containsString("bye"));
    }

    @Test
    public void itReturnsNotFoundPageWhenItemIdDoesNotExist() throws IOException {
        when(request.getPath()).thenReturn("/todo/15");
        when(request.getMethod()).thenReturn("GET");
        String htmlString = controller.getTodoDetail.apply(request);
        
        assertThat(htmlString, containsString("File Not Found"));
    } 
}
