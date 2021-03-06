package webserver.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.containsString;

import webserver.data.TodoService;
import webserver.models.Todo;
import webserver.request.Request;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {
    List<Todo> todos;
    Todo todo1;
    Todo todo2;
    
    @InjectMocks
    TodoController controller;
    
    @Mock
    Request request;
    
    @Mock
    TodoService todoService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        todo1 = new Todo("hello", "bye", 1, false);
        todo2 = new Todo("listen to spotify", "all day", 2, true);
        todos = Arrays.asList(todo1, todo2);
    }

    @Test
    public void itReturnsAllTodoItems() throws IOException {
        when(todoService.getTodos()).thenReturn(todos);
        String htmlString = controller.getTodoList.apply(request);

        assertThat(htmlString, containsString("hello"));
    }

    @Test
    public void itReturnsOneTodoItemWithDetails() throws IOException {
        when(request.getPath()).thenReturn("/hey/2");
        when(todoService.getTodos()).thenReturn(todos);
        String htmlString = controller.getTodoDetail.apply(request);

        assertThat(htmlString, containsString("spotify"));
    }

    @Test
    public void itReturnsNotFoundPageWhenItemIdDoesNotExist() throws IOException {
        when(request.getPath()).thenReturn("/hey/15");
        String htmlString = controller.getTodoDetail.apply(request);
        
        assertThat(htmlString, containsString("File Not Found"));
    } 

    @Test
    public void itReturnsAFilteredListOfTodos() throws IOException { 
        var query = Map.of("filter", "day");
        when(request.getQuery()).thenReturn(query);
        when(todoService.getTodos()).thenReturn(todos);
        String htmlString = controller.getFilteredList.apply(request);
        
        assertThat(htmlString, containsString("listen to spotify"));
    }

    @Test
    public void itReturnsCreatedResponseWhenFormContentTypeIsAppropriate() throws IOException {
        var headers = Map.of("Content-Type", "application/x-www-form-urlencoded");
        when(request.getHeaders()).thenReturn(headers);
        when(request.getBody()).thenReturn("name=eat%20lunch");
        String htmlString = controller.newTodo.apply(request);

        assertThat(htmlString, containsString("201 Created"));
    }

    @Test
    public void itReturnsBadRequestResponseWhenFormContentTypeIsAppropriateButBodyIsInvalid() throws IOException {
        var headers = Map.of("Content-Type", "application/x-www-form-urlencoded");
        when(request.getHeaders()).thenReturn(headers);
        when(request.getBody()).thenReturn("name=eat lunch");
        String htmlString = controller.newTodo.apply(request);

        assertThat(htmlString, containsString("400 Bad Request"));
    }

    @Test
    public void itReturnsUnsupportedMediaTypeResponseWhenFormContentTypeIsNotAppropriate() throws IOException {
        var headers = Map.of("Content-Type", "application/xml");
        when(request.getHeaders()).thenReturn(headers);
        when(request.getBody()).thenReturn("name=eat%20lunch");
        String htmlString = controller.newTodo.apply(request);

        assertThat(htmlString, containsString("415 Unsupported Media Type"));
    }
}
