package todomanager;

import java.time.LocalDate;

/**
 * Entité Todo améliorée avec description et date d'échéance
 */
public class Todo {

    private Long id;
    private String titre;
    private String description;  // NOUVEAU
    private LocalDate dateCreation;  // NOUVEAU
    private LocalDate dateEcheance;  // NOUVEAU
    private TodoStatus status;

    public Todo() {
        this.status = TodoStatus.PENDING;
        this.dateCreation = LocalDate.now();
    }

    public Todo(String titre) {
        this();
        this.titre = titre;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    /**
     * Vérifie si la tâche est en retard
     */
    public boolean isOverdue() {
        if (dateEcheance == null || status == TodoStatus.DONE) {
            return false;
        }
        return LocalDate.now().isAfter(dateEcheance);
    }

    /**
     * Vérifie si la tâche arrive à échéance dans les N jours
     */
    public boolean isDueSoon(int days) {
        if (dateEcheance == null || status == TodoStatus.DONE) {
            return false;
        }
        LocalDate limite = LocalDate.now().plusDays(days);
        return dateEcheance.isBefore(limite) || dateEcheance.isEqual(limite);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateEcheance=" + dateEcheance +
                ", status=" + status +
                '}';
    }
}
