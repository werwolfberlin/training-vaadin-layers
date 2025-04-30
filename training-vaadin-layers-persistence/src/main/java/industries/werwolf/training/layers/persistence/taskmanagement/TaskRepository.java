package industries.werwolf.training.layers.persistence.taskmanagement;

import industries.werwolf.training.layers.persistence.util.Page;

import java.util.List;

public interface TaskRepository {
    void saveTask(Task task);

    List<Task> findAllTasks(Page page);
}
