package webserver.controllers;

import java.io.IOException;
import java.util.HashMap;

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
        when(jsonTodos.getTodoById(1)).thenReturn(todo);
        String htmlString = controller.getTodoDetail.apply(request);

        assertThat(htmlString, containsString("bye"));
    }

    @Test
    public void itReturnsNotFoundPageWhenItemIdDoesNotExist() throws IOException {
        when(request.getPath()).thenReturn("/todo/15");
        String htmlString = controller.getTodoDetail.apply(request);
        
        assertThat(htmlString, containsString("File Not Found"));
    } 

    @Test
    public void itReturnsAFilteredListOfTodos() throws IOException {
        JSONObject todo1 = new JSONObject();
        todo1.put("title","hello");
        todo1.put("text","bye");
        todo1.put("id", 1);
        todo1.put("done", false);
        JSONObject todo2 = new JSONObject();
        todo2.put("title","please and");
        todo2.put("text","thank you");
        todo2.put("id", 2);
        todo2.put("done", false);
        JSONArray todosArray = new JSONArray();
        todosArray.add(todo1); 
        todosArray.add(todo2);
        HashMap<String, String> query = new HashMap<>();
        query.put("filter", "thank you");
        
        when(request.getQuery()).thenReturn(query);
        when(jsonTodos.getAllTodos()).thenReturn(todosArray);

        String htmlString = controller.getFilteredList.apply(request);
        
        assertThat(htmlString, containsString("please and"));
    }
}
