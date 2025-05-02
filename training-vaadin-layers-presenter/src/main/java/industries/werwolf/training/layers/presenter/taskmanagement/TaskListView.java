package industries.werwolf.training.layers.presenter.taskmanagement;

import industries.werwolf.training.layers.persistence.taskmanagement.Task;

import java.util.List;

public interface TaskListView {
    void init(List<Task> tasks);

    void refreshTaskList();

    void clearInputFields();

    void showTaskAddedNotification();

    void openProgressDialog();

    void showProgress(double max, double current);

    void hideProgress();
}
