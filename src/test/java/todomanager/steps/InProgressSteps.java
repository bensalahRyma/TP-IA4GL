package todomanager.steps;

import io.cucumber.java.fr.*;
import todomanager.*;
import static org.junit.Assert.*;

public class InProgressSteps {

    private static final TodoContext context = new TodoContext();

//    @Etantdonnéque("une tâche existe avec le titre {string}")
//    public void une_tache_existe(String titre) {
//        context.service = new TodoService();
//        context.todo = context.service.createTodo(titre);
//    }

    @Quand("l'utilisateur démarre la tâche")
    public void demarrer_tache() {
        context.todo = context.service.startTodo(context.todo.getId());
    }

    @Alors("le statut de la tâche est {string}")
    public void verifier_statut(String statut) {
        assertEquals(TodoStatus.valueOf(statut), context.todo.getStatus());
    }
}
