package industries.werwolf.training.layers.ui.util;

import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;

import java.util.List;
import java.util.stream.Stream;

public class GridDataProviderAdapter<T, F extends GlobalFilter> extends AbstractDataProviderAdapter<T, F, Void> {

    private final FilterableDataProvider<T, F> dataProvider;

    public GridDataProviderAdapter(FilterableDataProvider<T, F> dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public Stream<T> fetchAll(int offset, int limit, F filter, List<SortOrder> sortOrders) {
        return dataProvider.fetch(offset, limit, filter, sortOrders);
    }

    @Override
    public long count(F filter) {
        return dataProvider.count(filter);
    }
}
