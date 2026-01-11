package todomanager.steps;


import todomanager.Todo;
import todomanager.TodoService;

import java.util.List;

public class TodoContext {
    public TodoService service;
    public Todo todo;
    public List<Todo> resultats;
    public Long todoId;
    public Exception exception;
}