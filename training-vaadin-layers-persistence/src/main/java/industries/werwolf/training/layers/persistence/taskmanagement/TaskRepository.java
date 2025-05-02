package industries.werwolf.training.layers.persistence.taskmanagement;

import industries.werwolf.training.layers.persistence.filter.TaskFilter;
import industries.werwolf.training.layers.persistence.util.SortOrder;

import java.util.List;

public interface TaskRepository {
    void save(Task task);

    List<Task> findAllTasks(int offset, int limit, TaskFilter filter, List<SortOrder> sortOrders);

    long countAllTasks(TaskFilter filter);
}
