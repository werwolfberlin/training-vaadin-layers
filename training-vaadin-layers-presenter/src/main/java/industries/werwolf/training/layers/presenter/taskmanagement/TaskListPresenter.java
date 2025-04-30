package industries.werwolf.training.layers.presenter.taskmanagement;

import industries.werwolf.training.layers.service.taskmanagement.TaskService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskListPresenter {

    private final TaskService taskService;

    private TaskListView view;

    public TaskListPresenter(TaskService taskService) {
        this.taskService = taskService;
    }


    public void viewInitialized(TaskListView view) {
        this.view = view;

        view.init(taskService.getTaskListDataProvider());
    }

    public void createTaskClicked(String description, LocalDate dueDate) {
        taskService.createTask(description, dueDate);
        view.refreshTaskList();
        view.clearInputFields();
        view.showTaskAddedNotification();
    }
}
