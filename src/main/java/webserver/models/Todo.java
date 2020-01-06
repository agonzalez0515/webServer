package webserver.models;

import org.json.simple.JSONObject;

public class Todo {
    private String title;
    private String text;
    private int id;
    private boolean done;

    public Todo(String title, String text, int id, boolean done) {
        this.title = title;
        this.text = text;
        this.id = id;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public boolean getDone() {
        return done;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public static Todo fromJson(JSONObject todoObj) {
        String title = todoObj.get("title").toString();
        String text = todoObj.get("text").toString();
        int id = Integer.parseInt(todoObj.get("id").toString());
        boolean isDone = Boolean.parseBoolean(todoObj.get("done").toString());
        
        return new Todo(title, text, id, isDone);
    }

    @SuppressWarnings("unchecked")
    public static JSONObject toJson(String title, String text, int id, boolean isDone) {
        JSONObject todoObj = new JSONObject();
        todoObj.put("id", id);
        todoObj.put("title", title);
        todoObj.put("text", text);
        todoObj.put("done", isDone);

        return todoObj;
    }
}