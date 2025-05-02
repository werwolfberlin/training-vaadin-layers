package industries.werwolf.training.layers.presenter.taskmanagement;

import industries.werwolf.training.layers.persistence.filter.TaskFilter;
import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;

public interface TaskListView {
    void init(FilterableDataProvider<Task, TaskFilter> taskListDataProvider);

    void refreshTaskList();

    void clearInputFields();

    void showTaskAddedNotification();

    void openProgressDialog();

    void showProgress(double max, double current);

    void hideProgress();
}
