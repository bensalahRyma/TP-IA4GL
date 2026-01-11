package todomanager.steps;

import io.cucumber.java.fr.*;
import todomanager.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import static org.junit.Assert.*;

public class DescriptionDateSteps {

    private static final TodoContext context = new TodoContext();

//    @Etantdonnéque("le système TodoManager est disponible")
//    public void le_systeme_est_disponible() {
//        context.service = new TodoService();
//        context.exception = null;
//    }
//
//    @Etantdonnéque("une tâche existe avec le titre {string}")
//    public void une_tache_existe(String titre) {
//        context.todo = context.service.createTodo(titre);
//    }

    @Quand("l'utilisateur ajoute la description {string}")
    public void ajouter_description(String description) {
        context.todo.setDescription(description);
        context.service.update(context.todo);
    }

    @Quand("l'utilisateur définit la date d'échéance {string}")
    public void definir_date_echeance(String date) {
        context.todo.setDateEcheance(LocalDate.parse(date));
        context.service.update(context.todo);
    }

    @Alors("la description est {string}")
    public void verifier_description(String description) {
        assertEquals(description, context.todo.getDescription());
    }

    @Alors("la date d'échéance est {string}")
    public void verifier_date_echeance(String date) {
        assertEquals(LocalDate.parse(date), context.todo.getDateEcheance());
    }
}