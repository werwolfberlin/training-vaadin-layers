package industries.werwolf.training.layers.presenter.taskmanagement;

import industries.werwolf.training.layers.persistence.taskmanagement.Task;

import java.util.Collection;

public interface TaskListView {
    void init(Collection<Task> tasks);

    void refreshTaskList();

    void clearInputFields();

    void showTaskAddedNotification();
}
