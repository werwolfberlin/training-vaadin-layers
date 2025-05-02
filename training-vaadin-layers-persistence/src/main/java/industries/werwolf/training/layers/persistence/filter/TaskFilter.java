package industries.werwolf.training.layers.persistence.filter;

import java.time.LocalDate;

public class TaskFilter extends GlobalFilter {
    private final Range<LocalDate> dateRange;

    public TaskFilter(String searchTerm, Range<LocalDate> dateRange) {
        super(searchTerm);
        this.dateRange = dateRange;
    }

    public Range<LocalDate> getDateRange() {
        return dateRange;
    }
}
