package webserver;

public class Todo {
    private String title;
    private String text;
    private int id;

    public Todo(String title, String text, int id) {
        this.title = title;
        this.text = text;
        this.id = id;
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

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}