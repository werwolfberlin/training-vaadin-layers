package industries.werwolf.training.layers.persistence.filter;

public class GlobalFilter {
    private final String searchTerm;

    public GlobalFilter(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
}
