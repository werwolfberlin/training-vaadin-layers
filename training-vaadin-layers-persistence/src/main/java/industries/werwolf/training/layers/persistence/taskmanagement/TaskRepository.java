package industries.werwolf.training.layers.persistence.taskmanagement;

import java.util.List;

public interface TaskRepository {
    void save(Task task);

    List<Task> findAllTasks();
}
