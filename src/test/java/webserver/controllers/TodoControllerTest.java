package webserver.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.containsString;

import webserver.request.Request;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {
    @Mock
    Request request;

    @Test
    public void testItReturnsAllTodoItems() throws IOException {
        when(request.getMethod()).thenReturn("GET");
        String htmlString = TodoController.getTodoList.apply(request);

        assertThat(htmlString, containsString("Walk the dog"));
    }

    @Test
    public void testItReturnsOneTodoItemWithDetails() throws IOException {
        when(request.getPath()).thenReturn("/todo/1");
        when(request.getMethod()).thenReturn("GET");
        String htmlString = TodoController.getTodoDetail.apply(request);

        assertThat(htmlString, containsString("Go to target for milk"));
    }

    @Test
    public void testItReturnsNotFoundPageWhenItemIdDoesNotExist() throws IOException {
        when(request.getPath()).thenReturn("/todo/15");
        when(request.getMethod()).thenReturn("GET");
        String htmlString = TodoController.getTodoDetail.apply(request);
        
        assertThat(htmlString, containsString("File Not Found"));
    } 

    @Test
    public void itRedirectsAfterPostIsComplete() throws IOException {
        HashMap<String, String> body = new HashMap<String, String>();
        body.put("done", "true");
        when(request.getRequestBody()).thenReturn(body);
        when(request.getRequestPath()).thenReturn("/todo/4/toggle");
        String responseAfterPost = TodoController.updateTodoDetail.apply(request);

        assertThat(responseAfterPost, containsString("303"));
    }
}
