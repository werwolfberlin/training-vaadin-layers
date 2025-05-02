package industries.werwolf.training.layers.service.util;

import industries.werwolf.training.layers.persistence.util.SortOrder;

import java.util.List;
import java.util.stream.Stream;

public interface FilterableDataProvider<T, F> {
    Stream<T> fetch(int offset, int limit, F filter, List<SortOrder> sortOrders);
    long count(F filter);
}
