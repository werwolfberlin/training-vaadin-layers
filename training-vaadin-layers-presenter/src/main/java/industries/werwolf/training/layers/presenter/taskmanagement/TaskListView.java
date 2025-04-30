package industries.werwolf.training.layers.presenter.taskmanagement;

import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;

public interface TaskListView {
    void init(FilterableDataProvider<Task> taskListDataProvider);

    void refreshTaskList();

    void clearInputFields();

    void showTaskAddedNotification();
}
