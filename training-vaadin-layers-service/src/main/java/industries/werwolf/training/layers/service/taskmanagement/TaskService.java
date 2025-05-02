package industries.werwolf.training.layers.service.taskmanagement;

import industries.werwolf.training.layers.persistence.jpa.taskmanagement.TaskJpa;
import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.persistence.taskmanagement.TaskRepository;
import industries.werwolf.training.layers.persistence.util.Page;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final Clock clock;

    TaskService(TaskRepository taskRepository, Clock clock) {
        this.taskRepository = taskRepository;
        this.clock = clock;
    }

    public void createTask(String description, @Nullable LocalDate dueDate) {
        if ("fail".equals(description)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var task = new TaskJpa();
        task.setDescription(description);
        task.setCreationDate(clock.instant());
        task.setDueDate(dueDate);
        taskRepository.save(task);
    }

    public List<Task> list(Page page) {
        return taskRepository.findAllTasks(page);
    }

    public FilterableDataProvider<Task> getTaskListDataProvider() {
        return page -> taskRepository.findAllTasks(page).stream();
    }
}
