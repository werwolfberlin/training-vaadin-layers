package industries.werwolf.training.layers.persistence.util;

import java.util.List;

public record Page(int page, int pageSize, List<SortOrder> sortOrders) {
    public static Page of(int page, int pageSize, List<SortOrder> sortOrders) {
        return new Page(page + 1, pageSize, sortOrders);
    }

    public static Page ofSize(int size) {
        return new Page(1, size, List.of());
    }
}
