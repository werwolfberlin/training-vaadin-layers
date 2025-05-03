package industries.werwolf.training.layers.persistence.jpa.taskmanagement;

import industries.werwolf.training.layers.persistence.filter.TaskFilter;
import industries.werwolf.training.layers.persistence.jpa.base.AbstractDataProviderRepository;
import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.persistence.taskmanagement.TaskRepository;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.TypedQuery;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskRepositoryImpl extends AbstractDataProviderRepository<TaskFilter> implements TaskRepository {

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
        TypedQuery<TaskJpa> query = createQuery("SELECT t FROM Task t", filter, sortOrders.stream().map(so -> new SortOrder("t." + so.property(), so.direction())).toList(), TaskJpa.class);
        return query.setFirstResult(offset).setMaxResults(limit).getResultStream().map(Task.class::cast).toList();
    }

    @Override
    public long countAllTasks(TaskFilter filter) {
        TypedQuery<Long> query = createQuery("SELECT count(t) FROM Task t", filter, null, Long.class);
        return query.getSingleResult();
    }
    @Override
    protected void processFilter(@Nullable TaskFilter filter, @NonNull List<String> where, @NonNull Map<String, Object> params) {
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
    }
}
