package webserver;

import java.io.File;
import java.io.FileReader;
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
    private JSONArray todosArray = null;
    private File todosFile;

    public JsonTodos(String fileName) {
        this.todosFile = FileUtils.fileFromDirectory(fileName);
    }

    public JSONArray getAllTodos() {
        try (Reader reader = new FileReader(todosFile)) {
            todosArray = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return todosArray;
    }

    
    public JSONObject getTodoById(int id) {
        try (Reader reader = new FileReader(todosFile)) {
            todosArray = (JSONArray) parser.parse(reader);
            for (JSONObject todoObj: (Iterable<JSONObject>) todosArray) {
                int idToMatch = Integer.parseInt(todoObj.get("id").toString());
                if(id == idToMatch) {
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

        try (FileWriter file = new FileWriter(todosFile)) {
            file.write(todos.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTodo(JSONObject updatedTodo) {
        JSONArray todos = getAllTodos();
        todos.add(updatedTodo);

        try (FileWriter file = new FileWriter(todosFile)) {
            file.write(todos.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
