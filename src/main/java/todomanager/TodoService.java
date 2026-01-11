package todomanager;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service métier TodoManager amélioré
 */
public class TodoService {

    private Map<Long, Todo> todos;
    private Long currentId;

    public TodoService() {
        this.todos = new HashMap<>();
        this.currentId = 1L;
    }

    /**
     * Créer une nouvelle tâche
     */
    public Todo createTodo(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre est requis");
        }

        Todo todo = new Todo(titre);
        todo.setId(currentId++);
        todos.put(todo.getId(), todo);
        return todo;
    }

    /**
     * Rechercher une tâche par ID
     */
    public Todo findById(Long id) {
        Todo todo = todos.get(id);
        if (todo == null) {
            throw new TodoNotFoundException("Tâche introuvable");
        }
        return todo;
    }

    /**
     * Lister toutes les tâches
     */
    public List<Todo> findAll() {
        return new ArrayList<>(todos.values());
    }

    /**
     * Mettre à jour une tâche
     */
    public Todo update(Todo todo) {
        if (todo.getId() == null || !todos.containsKey(todo.getId())) {
            throw new TodoNotFoundException("Tâche introuvable");
        }

        if (todo.getTitre() == null || todo.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }

        todos.put(todo.getId(), todo);
        return todo;
    }

    /**
     * Supprimer une tâche
     */
    public void delete(Long id) {
        if (!todos.containsKey(id)) {
            throw new TodoNotFoundException("Tâche introuvable");
        }
        todos.remove(id);
    }

    // ========================================
    // NOUVELLES MÉTHODES - Gestion des statuts
    // ========================================

    /**
     * Démarrer une tâche (PENDING -> IN_PROGRESS)
     */
    public Todo startTodo(Long id) {
        Todo todo = findById(id);

        if (todo.getStatus() == TodoStatus.DONE) {
            throw new IllegalStateException("Impossible de démarrer une tâche terminée");
        }

        if (todo.getStatus() == TodoStatus.IN_PROGRESS) {
            throw new IllegalStateException("La tâche est déjà en cours");
        }

        todo.setStatus(TodoStatus.IN_PROGRESS);
        return todo;
    }

    /**
     * Finaliser une tâche (IN_PROGRESS -> DONE ou PENDING -> DONE)
     */
    public Todo completeTodo(Long id) {
        Todo todo = findById(id);

        if (todo.getStatus() == TodoStatus.DONE) {
            // Idempotent - ne génère pas d'erreur
            return todo;
        }

        todo.setStatus(TodoStatus.DONE);
        return todo;
    }

    /**
     * Remettre une tâche en attente (transition invalide pour DONE)
     */
    public Todo resetToPending(Long id) {
        Todo todo = findById(id);

        if (todo.getStatus() == TodoStatus.DONE) {
            throw new IllegalStateException("Impossible de modifier le statut d'une tâche terminée");
        }

        todo.setStatus(TodoStatus.PENDING);
        return todo;
    }

    // ========================================
    // NOUVELLES MÉTHODES - Filtrage et recherche
    // ========================================

    /**
     * Filtrer par statut
     */
    public List<Todo> findByStatus(TodoStatus status) {
        return todos.values().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Rechercher dans titre et description
     */
    public List<Todo> search(String terme) {
        String termeLower = terme.toLowerCase();
        return todos.values().stream()
                .filter(t -> {
                    boolean inTitle = t.getTitre() != null &&
                            t.getTitre().toLowerCase().contains(termeLower);
                    boolean inDesc = t.getDescription() != null &&
                            t.getDescription().toLowerCase().contains(termeLower);
                    return inTitle || inDesc;
                })
                .collect(Collectors.toList());
    }

    /**
     * Trouver les tâches arrivant à échéance
     */
    public List<Todo> findDueSoon(int days) {
        return todos.values().stream()
                .filter(t -> t.isDueSoon(days))
                .collect(Collectors.toList());
    }

    /**
     * Trouver les tâches en retard
     */
    public List<Todo> findOverdue() {
        return todos.values().stream()
                .filter(Todo::isOverdue)
                .collect(Collectors.toList());
    }

    /**
     * Pagination
     */
    public PaginationResult findAllPaginated(int page, int pageSize) {
        List<Todo> allTodos = findAll();
        int totalElements = allTodos.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalElements);

        List<Todo> pageContent = start < totalElements
                ? allTodos.subList(start, end)
                : Collections.emptyList();

        return new PaginationResult(pageContent, page, totalPages, totalElements);
    }

    /**
     * Trier par date de création
     */
    public List<Todo> sortByDateCreation(boolean ascending) {
        List<Todo> sorted = new ArrayList<>(todos.values());
        sorted.sort((t1, t2) -> {
            int comparison = t1.getDateCreation().compareTo(t2.getDateCreation());
            return ascending ? comparison : -comparison;
        });
        return sorted;
    }

    /**
     * Trier par date d'échéance
     */
    public List<Todo> sortByDateEcheance(boolean ascending) {
        List<Todo> sorted = todos.values().stream()
                .filter(t -> t.getDateEcheance() != null)
                .collect(Collectors.toList());

        sorted.sort((t1, t2) -> {
            int comparison = t1.getDateEcheance().compareTo(t2.getDateEcheance());
            return ascending ? comparison : -comparison;
        });
        return sorted;
    }
}
