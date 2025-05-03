package industries.werwolf.training.layers.persistence.jpa.base;

import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDataProviderRepository<F extends GlobalFilter> {
    @PersistenceContext
    private jakarta.persistence.EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    protected abstract void processFilter(@Nullable F filter, @NonNull List<String> where, @NonNull Map<String, Object> params);

    protected  <T> TypedQuery<T> createQuery(String sql, F filter, @Nullable List<SortOrder> sortOrders, Class<T> resultClass) {
        List<String> where = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        processFilter(filter, where, params);

        String select = sql + getWhere(where) + getOrderBy(sortOrders);
        TypedQuery<T> query = entityManager.createQuery(select, resultClass);
        params.forEach(query::setParameter);
        return query;
    }

    @NonNull
    private static String getWhere(List<String> where) {
        return where.isEmpty() ? "" : " WHERE " + String.join(" AND ", where);
    }

    @NonNull
    private static String getOrderBy(@Nullable List<SortOrder> sortOrders) {
        return sortOrders == null || sortOrders.isEmpty() ? "" : " ORDER BY " +
                sortOrders.stream()
                        .map(so -> so.property() + " " + so.direction().name())
                        .collect(Collectors.joining(", "));
    }
}
