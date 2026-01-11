package todomanager;

import java.util.List;

/**
 * Classe pour encapsuler les résultats paginés
 */
public class PaginationResult {

    private List<Todo> taches;
    private int pageActuelle;
    private int totalPages;
    private int totalElements;

    public PaginationResult(List<Todo> taches, int pageActuelle, int totalPages, int totalElements) {
        this.taches = taches;
        this.pageActuelle = pageActuelle;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<Todo> getTaches() {
        return taches;
    }

    public void setTaches(List<Todo> taches) {
        this.taches = taches;
    }

    public int getPageActuelle() {
        return pageActuelle;
    }

    public void setPageActuelle(int pageActuelle) {
        this.pageActuelle = pageActuelle;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean hasNext() {
        return pageActuelle < totalPages;
    }

    public boolean hasPrevious() {
        return pageActuelle > 1;
    }

    @Override
    public String toString() {
        return "PaginationResult{" +
                "taches=" + taches.size() +
                ", pageActuelle=" + pageActuelle +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                '}';
    }
}
