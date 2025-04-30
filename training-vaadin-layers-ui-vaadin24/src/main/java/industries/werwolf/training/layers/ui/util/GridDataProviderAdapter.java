package industries.werwolf.training.layers.ui.util;

import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import industries.werwolf.training.layers.persistence.util.Page;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;

import java.util.stream.Stream;

public class GridDataProviderAdapter<T> implements CallbackDataProvider.FetchCallback<T, Void> {

    private final FilterableDataProvider<T> dataProvider;

    public GridDataProviderAdapter(FilterableDataProvider<T> dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public Stream<T> fetch(Query<T, Void> query) {
        return dataProvider.fetch(Page.of(query.getPage(), query.getPageSize(),
                        query.getSortOrders().stream().map(GridDataProviderAdapter::toSortOrder).toList()));
    }

    private static SortOrder toSortOrder(QuerySortOrder so) {
        return new SortOrder(so.getSorted(), so.getDirection() == SortDirection.ASCENDING ? SortOrder.Direction.ASC : SortOrder.Direction.DESC);
    }
}
