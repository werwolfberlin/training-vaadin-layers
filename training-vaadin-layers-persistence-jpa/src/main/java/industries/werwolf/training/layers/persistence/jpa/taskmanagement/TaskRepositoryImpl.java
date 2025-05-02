package industries.werwolf.training.layers.persistence.jpa.taskmanagement;

import industries.werwolf.training.layers.persistence.filter.TaskFilter;
import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.persistence.taskmanagement.TaskRepository;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskRepositoryImpl implements TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final TaskRepositoryJpa repository;

    public TaskRepositoryImpl(TaskRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public void save(Task task) {
        if(!(task instanceof TaskJpa taskJpa)){
            throw new IllegalArgumentException("Task must be an instance of TaskJpa");
        }
        repository.saveAndFlush(taskJpa);
    }

    @Override
    public List<Task> findAllTasks(int offset, int limit, @Nullable TaskFilter filter, List<SortOrder> sortOrders) {
        List<String> sort = sortOrders.stream().map(so -> "t." + so.property() + " " + so.direction().name()).toList();
        TypedQuery<TaskJpa> query = createQuery("SELECT t FROM Task t", filter, sort, TaskJpa.class);
        return query.setFirstResult(offset).setMaxResults(limit).getResultStream().map(Task.class::cast).toList();
    }

    @Override
    public long countAllTasks(TaskFilter filter) {
        TypedQuery<Long> query = createQuery("SELECT count(t) FROM Task t", filter, null, Long.class);
        return query.getSingleResult();
    }

    private <T> TypedQuery<T> createQuery(String sql,
                                          @Nullable TaskFilter filter,
                                          @Nullable List<String> sort,
                                          Class<T> resultClass) {

        Map<String, Object> params = new HashMap<>();
        List<String> where = new ArrayList<>();

        if(filter != null && StringUtils.isNotBlank(filter.getSearchTerm())) {
            where.add("t.description LIKE :searchTerm");
            params.put("searchTerm", "%" + filter.getSearchTerm() + "%");
        }

        if(filter != null && filter.getDateRange() != null) {
            if(filter.getDateRange().min() != null) {
                where.add("t.dueDate >= :minDate");
                params.put("minDate", filter.getDateRange().min());
            }
            if(filter.getDateRange().max() != null) {
                where.add("t.dueDate <= :maxDate");
                params.put("maxDate", filter.getDateRange().max());
            }
        }

        if(!where.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", where);
        }

        if(sort != null && !sort.isEmpty()) {
            sql += " ORDER BY " + String.join(", ", sort);
        }

        TypedQuery<T> query = entityManager.createQuery(sql, resultClass);
        params.forEach(query::setParameter);
        System.out.println(sql);
        return query;
    }
}
