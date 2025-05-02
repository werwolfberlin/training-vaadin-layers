package industries.werwolf.training.layers.ui.util;

import com.vaadin.flow.data.provider.Query;
import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;

import java.util.List;
import java.util.stream.Stream;

public class ComboBoxDataProviderAdapter<T> extends AbstractDataProviderAdapter<T, GlobalFilter, String> {

    private final FilterableDataProvider<T, GlobalFilter> dataProvider;

    public ComboBoxDataProviderAdapter(FilterableDataProvider<T, GlobalFilter> dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    protected GlobalFilter getFilter(Query<T, String> query) {
        return query.getFilter().map(GlobalFilter::new).orElse(null);
    }

    @Override
    public Stream<T> fetchAll(int offset, int limit, GlobalFilter filter, List<SortOrder> sortOrders) {
        return dataProvider.fetch(offset, limit, filter, sortOrders);
    }

    @Override
    public long count(GlobalFilter filter) {
        return dataProvider.count(filter);
    }
}
