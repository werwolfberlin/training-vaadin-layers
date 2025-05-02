package industries.werwolf.training.layers.ui.taskmanagement.ui.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.persistence.jpa.taskmanagement.TaskJpa;
import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.presenter.taskmanagement.TaskListPresenter;
import industries.werwolf.training.layers.presenter.taskmanagement.TaskListView;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;
import industries.werwolf.training.layers.ui.base.ui.component.ViewToolbar;
import industries.werwolf.training.layers.ui.base.ui.view.MainLayoutImpl;
import industries.werwolf.training.layers.ui.util.GridDataProviderAdapter;
import jakarta.annotation.security.PermitAll;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

@PermitAll
@Route(value = "task-list", layout = MainLayoutImpl.class)
@PageTitle("Task List")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task List")
public class TaskListViewImpl extends Main implements TaskListView {

    private final TaskListPresenter presenter;

    final TextField description = new TextField();
    final DatePicker dueDate = new DatePicker();
    final Button createBtn;
    final Grid<Task> taskGrid;

    public TaskListViewImpl(TaskListPresenter presenter, Clock clock) {
        this.presenter = presenter;

        description.setPlaceholder("What do you want to do?");
        description.setAriaLabel("Task description");
        description.setMaxLength(TaskJpa.DESCRIPTION_MAX_LENGTH);
        description.setMinWidth("20em");

        dueDate.setPlaceholder("Due date");
        dueDate.setAriaLabel("Due date");

        createBtn = new Button("Create", event -> presenter.createTaskClicked(description.getValue(), dueDate.getValue()));
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone()).withLocale(getLocale());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        taskGrid = new Grid<>();

        taskGrid.addColumn(Task::getDescription).setHeader("Description");
        taskGrid.addColumn(task -> Optional.ofNullable(task.getDueDate()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Due Date");
        taskGrid.addColumn(task -> dateTimeFormatter.format(task.getCreationDate())).setHeader("Creation Date");
        taskGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Task List", ViewToolbar.group(description, dueDate, createBtn)));
        add(taskGrid);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if(attachEvent.isInitialAttach()) {
            presenter.viewInitialized(this);
        }
    }

    @Override
    public void init(FilterableDataProvider<Task> tasks) {
        taskGrid.setItems(new GridDataProviderAdapter<>(tasks));
    }

    @Override
    public void refreshTaskList() {
        taskGrid.getDataProvider().refreshAll();
    }

    @Override
    public void clearInputFields() {
        description.clear();
        dueDate.clear();
    }

    @Override
    public void showTaskAddedNotification() {
        Notification.show("Task added", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
