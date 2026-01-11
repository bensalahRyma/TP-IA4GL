package todomanager.steps;

import io.cucumber.java.fr.Etantdonnéque;
import todomanager.Todo;
import todomanager.TodoService;

public class CommonTodoSteps {

    protected static final TodoContext context = new TodoContext();

    @Etantdonnéque("le système TodoManager est disponible")
    public void systeme_disponible() {
        context.service = new TodoService();
        context.todo = null;
        context.exception = null;
    }

    @Etantdonnéque("une tâche existe avec le titre {string}")
    public void une_tache_existe(String titre) {
        context.todo = context.service.createTodo(titre);
    }
}