package industries.werwolf.training.layers.persistence.jpa.taskmanagement;

import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.persistence.taskmanagement.TaskRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskRepositoryImpl implements TaskRepository {

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
    public Collection<Task> findAll() {
        return repository.findAll().stream().map(Task.class::cast).toList();
    }
}
