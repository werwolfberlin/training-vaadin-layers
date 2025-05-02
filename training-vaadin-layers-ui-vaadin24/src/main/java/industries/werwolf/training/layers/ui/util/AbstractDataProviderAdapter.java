package industries.werwolf.training.layers.ui.util;

import com.vaadin.flow.data.provider.AbstractDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.persistence.util.SortOrder;

import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractDataProviderAdapter<T, F extends GlobalFilter, CF> extends AbstractDataProvider<T, CF> {

    protected F filter;

    public void setFilter(F filter) {
        this.filter = filter;
    }

    protected static SortOrder toSortOrder(QuerySortOrder so) {
        return new SortOrder(so.getSorted(), so.getDirection() == SortDirection.ASCENDING ? SortOrder.Direction.ASC : SortOrder.Direction.DESC);
    }

    protected F getFilter(Query<T, CF> query) {
        return filter;
    }

    public abstract Stream<T> fetchAll(int offset, int limit, F filter, List<SortOrder> sortOrders);

    public abstract long count(F filter);

    @Override
    public int size(Query<T, CF> query) {
        return (int) count(getFilter(query));
    }

    @Override
    public Stream<T> fetch(Query<T, CF> query) {
        return fetchAll(query.getOffset(), query.getLimit(), getFilter(query), query.getSortOrders().stream().map(AbstractDataProviderAdapter::toSortOrder).toList());
    }

    @Override
    public boolean isInMemory() {
        return false;
    }
}
