package webserver.data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import webserver.FileUtils;

@SuppressWarnings("unchecked")
public class JsonClient  {
    private static JSONParser parser = new JSONParser();
    private FileUtils fileUtils  = new FileUtils();
    private JSONArray todosArray = null;
    private String todoFilePath;

    public JsonClient(String directory) {
        this.todoFilePath = "/" + directory + "/todos.json";
    }

    public JSONArray getAllItems() {
        try (Reader reader = fileUtils.fileReader(todoFilePath)) {
            todosArray = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return todosArray;
    }
    
    public JSONObject getItemById(int id) {
        try (Reader reader = fileUtils.fileReader(todoFilePath)) {
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

    public void addItem(JSONObject newItem) {
        JSONArray todos = getAllItems();
        todos.add(newItem);

        try (FileWriter file = fileUtils.fileWriter(todoFilePath)) {
            file.write(todos.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateItemById(int id, JSONObject updatedItem) {
        JSONArray todos = getAllItems();
        try (FileWriter file = fileUtils.fileWriter(todoFilePath)) {
            for (int i = 0; i < todos.size(); i++) {
                JSONObject obj = (JSONObject) todos.get(i);
                int idToMatch = Integer.parseInt(obj.get("id").toString());
                if (id == idToMatch) {
                    todos.remove(i);
                    todos.add(updatedItem);
                }
            }
            file.write(todos.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteItemById(int id) {
        JSONArray todos = getAllItems();
        try (FileWriter file = fileUtils.fileWriter(todoFilePath)) {
            for (int i = 0; i < todos.size(); i++) {
                JSONObject obj = (JSONObject) todos.get(i);
                int idToMatch = Integer.parseInt(obj.get("id").toString());
                if (id == idToMatch) {
                    todos.remove(i);
                }
            }
            file.write(todos.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
