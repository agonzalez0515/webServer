package webserver;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class JsonTodosTest {

    @Test
    public void testItReturnsAGroupOfTodoItems() {
        JsonTodos todos = new JsonTodos("/src/test/resources/testJson.json");
        JSONArray items = todos.getAllTodos();
        JSONObject actual = (JSONObject) items.get(0);

        assertTrue(actual.containsValue("Arrow"));
    }

    @Test
    public void testItUpdatesTodoListWithNewTodoItem() {
        JsonTodos todos = new JsonTodos("/src/test/resources/testJson.json");
        JSONObject newTodo = new JSONObject();
        newTodo.put("id", 3);
        newTodo.put("title", "Supergirl");
        todos.addTodo(newTodo);
        JSONArray items = todos.getAllTodos();
        JSONObject actual = (JSONObject) items.get(2);

        assertTrue(actual.containsValue("Supergirl"));
    }

    @Test
    public void testItUpdatesAnExistingTodoItem() {
        JsonTodos todos = new JsonTodos("/src/test/resources/testJson.json");
        JSONObject newTodo = new JSONObject();
        newTodo.put("id", 4);
        newTodo.put("title", "Buffy The Vampire Slayer");
        todos.updateTodo(newTodo);
        JSONArray items = todos.getAllTodos();
        JSONObject actual = (JSONObject) items.get(3);

        assertTrue(actual.containsValue("Buffy The Vampire Slayer"));
    }

    @Test
    public void testItReturnsOneTodo() {
        JsonTodos todos = new JsonTodos("/src/test/resources/testJson.json");
        JSONObject actual = todos.getTodoById(2);

        assertTrue(actual.containsValue("The Flash"));
    }
}
