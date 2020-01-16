                              package webserver.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import webserver.helpers.TestHelper;
import webserver.data.JsonClient;
import webserver.models.Todo;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class JsonClientTest {

    @Before
    public void setUpData() {
        TestHelper.copyFile("src/test/resources/test.json", "src/test/resources/todos.json");
    }

    @After
    public void clearData() {
        TestHelper.clearFile("src/test/resources/todos.json");
    }

    @Test
    public void itReturnsAGroupOfTodoItems() {
        JsonClient todos = new JsonClient("/src/test/resources");
        JSONArray items = todos.getAllItems();
        JSONObject actual = (JSONObject) items.get(0);

        assertThat(actual.size(), greaterThan(0));
    }

    @Test
    public void itUpdatesTodoListWithNewTodoItem() {
        JsonClient todos = new JsonClient("/src/test/resources");
        JSONObject newTodo = new JSONObject();
        newTodo.put("id", 100);
        newTodo.put("title", "Supergirl");
        todos.addItem(newTodo);
        JSONObject actual = todos.getItemById(100);

        assertTrue(actual.containsValue("Supergirl"));
    }

    @Test
    public void itUpdatesAnExistingTodoItem() {
        JsonClient todos = new JsonClient("/src/test/resources");
        Todo updatedTodo = new Todo("podcasts", "listen to lots", 1, true);
        todos.updateItemById(1, Todo.toJson(updatedTodo));
        JSONObject actual = todos.getItemById(1);

        assertTrue(actual.containsValue("podcasts"));
    }

    @Test
    public void itReturnsOneTodo() {
        JsonClient todos = new JsonClient("/src/test/resources");
        JSONObject actual = todos.getItemById(1);

        assertNotNull(actual);
    } 

    @Test
    public void itDeletesATodo() {
        JsonClient todos = new JsonClient("/src/test/resources");
        todos.deleteItemById(1);
        JSONObject actual = todos.getItemById(1);
        
        assertNull(actual);
    }
}
