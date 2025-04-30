package industries.werwolf.training.layers.persistence.jpa.util;

import industries.werwolf.training.layers.persistence.util.Page;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class SpringFrameworkConverter {
    private SpringFrameworkConverter() {
    }

    public static Sort convertSortOrder(Page page) {
        return Sort.by(page.sortOrders().stream().map(s -> s.direction() == SortOrder.Direction.ASC ? Sort.Order.asc(s.property()) : Sort.Order.desc(s.property())).toList());
    }

    public static PageRequest convertToPageRequest(Page page) {
        return PageRequest.of(page.page(), page.pageSize(), convertSortOrder(page));
    }
}
