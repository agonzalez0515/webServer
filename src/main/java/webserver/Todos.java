package webserver;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

interface Todos {

  public JSONArray getAllTodos();
  public void addTodo(JSONObject newTodo);
  public void updateTodo(JSONObject updatedTodo);
}
