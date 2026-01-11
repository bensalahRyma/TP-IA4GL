package todomanager.steps;

import io.cucumber.java.fr.*;
import io.cucumber.datatable.DataTable;
import todomanager.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class ListingFilterSteps {

    private static final TodoContext context = new TodoContext();

//    @Etantdonnéque("que le système TodoManager est disponible")
//    public void systeme_disponible() {
//        context.service = new TodoService();
//    }

    @Etantdonnéque("que les tâches suivantes existent :")
    public void taches_existent(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();

        for (Map<String, String> row : rows) {
            Todo todo = context.service.createTodo(row.get("titre"));

            if (row.get("date_creation") != null) {
                todo.setDateCreation(LocalDate.parse(row.get("date_creation")));
            }

            if (row.get("date_echeance") != null) {
                todo.setDateEcheance(LocalDate.parse(row.get("date_echeance")));
            }

            if ("IN_PROGRESS".equals(row.get("statut"))) {
                context.service.startTodo(todo.getId());
            }
            if ("DONE".equals(row.get("statut"))) {
                context.service.startTodo(todo.getId());
                context.service.completeTodo(todo.getId());
            }
        }
    }

    @Quand("l'utilisateur filtre les tâches par statut {string}")
    public void filtrer_par_statut(String statut) {
        context.resultats = context.service.findByStatus(TodoStatus.valueOf(statut));
    }

    @Alors("{int} tâche(s) est/sont affichée(s)")
    public void verifier_nombre(int nombre) {
        assertEquals(nombre, context.resultats.size());
    }

    @Alors("les tâches affichées ont le statut {string}")
    public void verifier_statut(String statut) {
        TodoStatus attendu = TodoStatus.valueOf(statut);
        context.resultats.forEach(t ->
                assertEquals(attendu, t.getStatus())
        );
    }
}