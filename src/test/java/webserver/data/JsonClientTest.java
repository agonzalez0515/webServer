package webserver.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import webserver.data.JsonClient;
import webserver.models.Todo;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class JsonClientTest {

    //TODO setup a before to seed the json and an after to clear the json file. 

    @Test
    public void testItReturnsAGroupOfTodoItems() {
        JsonClient todos = new JsonClient("/src/test/resources");
        JSONArray items = todos.getAllItems();
        JSONObject actual = (JSONObject) items.get(0);

        assertThat(actual.size(), greaterThan(0));
    }

    @Test
    public void testItUpdatesTodoListWithNewTodoItem() {
        JsonClient todos = new JsonClient("/src/test/resources");
        JSONObject newTodo = new JSONObject();
        newTodo.put("id", 3);
        newTodo.put("title", "Supergirl");
        todos.addItem(newTodo);
        JSONObject actual = todos.getItemById(3);

        assertTrue(actual.containsValue("Supergirl"));
    }

    @Test
    public void testItUpdatesAnExistingTodoItem() {
        JsonClient todos = new JsonClient("/src/test/resources");
        Todo updatedTodo = new Todo("podcasts", "listen to lots", 1, true);
        todos.updateItemById(1, Todo.toJson(updatedTodo));
        JSONObject actual = todos.getItemById(1);

        assertTrue(actual.containsValue("podcasts"));
    }

    @Test
    public void testItReturnsOneTodo() {
        JsonClient todos = new JsonClient("/src/test/resources");
        JSONObject actual = todos.getItemById(1);

        assertNotNull(actual);
    } 
}
