package webserver.data;

import java.util.ArrayList;
import java.util.List;

import webserver.models.Todo;

public class TodosHelper {
    public static List<Todo> getFilteredTodos(String query, List<Todo> todos) { 
        List<Todo> filteredTodos = new ArrayList<>();
        
        for (Todo todo : todos) {
            String title = todo.getTitle().toLowerCase();
            String text = todo.getText().toLowerCase();
            if (title.contains(query) || text.contains(query)) {
                filteredTodos.add(todo);
            }
        }
        return filteredTodos;
    }

    public static Todo getTodo(int id, List<Todo> todos) {
        return todos.stream()
                    .filter(todo -> id == todo.getId())
                    .findFirst()
                    .orElse(null);
    }
}
