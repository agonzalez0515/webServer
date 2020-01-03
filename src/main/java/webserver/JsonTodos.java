package webserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unchecked")
public class JsonTodos implements Todos {
    private static JSONParser parser = new JSONParser();
    private FileUtils fileUtils  = new FileUtils();
    private JSONArray todosArray = null;
    private String todosFile;

    public JsonTodos(String fileName) {
        this.todosFile = fileName;
    }

    public JSONArray getAllTodos() {
        try (Reader reader = fileUtils.fileReader(todosFile)) {
            todosArray = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return todosArray;
    }
    
    public JSONObject getTodoById(int id) {
        try (Reader reader = fileUtils.fileReader(todosFile)) {
            todosArray = (JSONArray) parser.parse(reader);
            for (JSONObject todoObj: (Iterable<JSONObject>) todosArray) {
                int idToMatch = Integer.parseInt(todoObj.get("id").toString());
                if (id == idToMatch) {
                    return todoObj;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTodo(JSONObject newTodo) {
        JSONArray todos = getAllTodos();
        todos.add(newTodo);

        try (FileWriter file = fileUtils.fileWriter(todosFile)) {
            file.write(todos.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTodo(int id, boolean isDone) {
        JSONArray todos = getAllTodos();
        try (FileWriter file = fileUtils.fileWriter(todosFile)) {
            for (JSONObject todo: (Iterable<JSONObject>) todos) {
                int idToMatch = Integer.parseInt(todo.get("id").toString());
                if (id == idToMatch) {
                    todo.put("done", isDone);
                }
            }
            
            file.write(todos.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
