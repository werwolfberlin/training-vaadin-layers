package industries.werwolf.training.layers.presenter.taskmanagement;

import industries.werwolf.training.layers.service.taskmanagement.TaskService;
import industries.werwolf.training.layers.service.util.ProgressListener;
import industries.werwolf.training.layers.service.util.Registration;
import industries.werwolf.training.layers.service.util.SecurityUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.concurrent.CompletableFuture;

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
        Snail snail = new Snail();
        Registration registration = snail.addProgressListener((max, current) -> view.showProgress(max, current));
        view.openProgressDialog();
        SecurityContext context = SecurityUtils.getContext();
        CompletableFuture
                .runAsync(() -> SecurityUtils.doInContext(context, () -> snail.creep(200, 100)))
                .whenComplete((result, error) ->
                        SecurityUtils.doInContext(context, () -> {
                            registration.remove();
                            view.hideProgress();
                            view.refreshTaskList();
                            view.clearInputFields();
                            view.showTaskAddedNotification();
                        }));
    }

    private static class Snail {
        // create a collection to save all the listeners
        private final Collection<ProgressListener> listeners = Collections.synchronizedSet(new LinkedHashSet<>());

        public void creep(int iterations, long sleepTime) {
            for (int i = 0; i < iterations; i++) {
                try {
                    Thread.sleep(sleepTime);
                    fireProgress(iterations, i + 1); // fire listener
                } catch (Exception ignored) {
                }
            }
        }

        private void fireProgress(double max, double current) { // iterate over the listeners and fire the values
            new LinkedHashSet<>(listeners).forEach(l -> l.setProgress(max, current));
        }

        public Registration addProgressListener(ProgressListener listener) { // register listener
            // Registration is a class which allows to keep a listener object and remove it to avoid memory leaks
            return Registration.addAndRemove(listeners, listener);
        }
    }

}
