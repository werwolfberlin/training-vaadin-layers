package industries.werwolf.training.layers.persistence.taskmanagement;

import java.util.Collection;

public interface TaskRepository {
    void save(Task task);

    Collection<Task> findAllTasks();
}
