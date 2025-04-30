package industries.werwolf.training.layers.persistence.jpa.taskmanagement;

import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.persistence.taskmanagement.TaskRepository;
import industries.werwolf.training.layers.persistence.util.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static industries.werwolf.training.layers.persistence.jpa.util.SpringFrameworkConverter.convertToPageRequest;

@Repository
public interface TaskRepositoryJpa extends TaskRepository, JpaRepository<TaskJpa, Long>, JpaSpecificationExecutor<TaskJpa> {

    // If you don't need a total row count, Slice is better than Page.
    Slice<TaskJpa> findAllBy(Pageable pageable);

    @Override
    default void saveTask(Task task) {
        if(!(task instanceof TaskJpa taskJpa)){
            throw new IllegalArgumentException("Task must be an instance of TaskJpa");
        }
        saveAndFlush(taskJpa);
    }

    default List<Task> findAllTasks(Page page) {
        return findAllBy(convertToPageRequest(page)).map(Task.class::cast).toList();
    }
}
