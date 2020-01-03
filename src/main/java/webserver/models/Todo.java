package webserver.models;

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
}