package todomanager.steps;

import io.cucumber.java.fr.*;
import todomanager.*;
import static org.junit.Assert.*;

/**
 * Step definitions pour les scénarios de base TodoManager
 */

public class TodoSteps {

    private final TodoContext context = CommonTodoSteps.context;

    @Quand("l'utilisateur crée une tâche avec le titre {string}")
    public void creer_tache(String titre) {
        try {
            context.todo = context.service.createTodo(titre);
        } catch (Exception e) {
            context.exception = e;
        }
    }

    @Alors("la tâche est créée avec le statut {string}")
    public void verifier_statut(String statut) {
        assertNotNull("La tâche devrait être créée", context.todo);
        assertEquals(TodoStatus.valueOf(statut), context.todo.getStatus());
    }

    @Quand("l'utilisateur crée une tâche sans titre")
    public void creer_tache_sans_titre() {
        try {
            context.service.createTodo(null);
            fail("Une exception aurait dû être levée");
        } catch (Exception e) {
            context.exception = e;
        }
    }

    @Alors("une erreur {string} est levée")
    public void erreur_levee(String message) {
        assertNotNull(context.exception);
        assertTrue(context.exception.getMessage().contains(message));
    }

    @Quand("l'utilisateur consulte la tâche")
    public void consulter_tache() {
        context.todo = context.service.findById(context.todo.getId());
    }

    @Alors("la tâche est affichée avec son titre {string}")
    public void verifier_titre(String titre) {
        assertEquals(titre, context.todo.getTitre());
    }

    @Quand("l'utilisateur consulte une tâche avec l'ID {int}")
    public void consulter_id(int id) {
        try {
            context.service.findById((long) id);
        } catch (Exception e) {
            context.exception = e;
        }
    }

    @Alors("une erreur {string} est retournée")
    public void erreur_retournee(String message) {
        assertNotNull(context.exception);
        assertTrue(context.exception.getMessage().contains(message));
    }

    @Quand("l'utilisateur modifie le titre en {string}")
    public void modifier_titre(String nouveauTitre) {
        try {
            context.todo.setTitre(nouveauTitre);
            context.service.update(context.todo);
        } catch (Exception e) {
            context.exception = e;
        }
    }

    @Alors("le titre de la tâche est {string}")
    public void titre_mis_a_jour(String titre) {
        assertEquals(titre, context.todo.getTitre());
    }

    @Quand("l'utilisateur tente de modifier le titre en {string}")
    public void modifier_titre_invalide(String titre) {
        try {
            context.todo.setTitre(titre);
            context.service.update(context.todo);
        } catch (Exception e) {
            context.exception = e;
        }
    }

    @Alors("une erreur de validation est levée")
    public void erreur_validation() {
        assertNotNull(context.exception);
    }

    @Quand("l'utilisateur finalise la tâche")
    public void finaliser() {
        context.todo = context.service.completeTodo(context.todo.getId());
    }

    @Alors("la tâche passe au statut {string}")
    public void statut_final(String statut) {
        assertEquals(TodoStatus.valueOf(statut), context.todo.getStatus());
    }

    @Quand("l'utilisateur supprime la tâche")
    public void supprimer() {
        context.service.delete(context.todo.getId());
    }

    @Alors("la tâche n'existe plus dans le système")
    public void tache_supprimee() {
        try {
            context.service.findById(context.todo.getId());
            fail();
        } catch (TodoNotFoundException e) {
            assertNotNull(e);
        }
    }
}