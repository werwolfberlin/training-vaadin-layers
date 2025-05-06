package industries.werwolf.training.layers.ui.taskmanagement.ui.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.persistence.filter.Range;
import industries.werwolf.training.layers.persistence.filter.TaskFilter;
import industries.werwolf.training.layers.persistence.jpa.taskmanagement.TaskJpa;
import industries.werwolf.training.layers.persistence.taskmanagement.Task;
import industries.werwolf.training.layers.presenter.taskmanagement.TaskListPresenter;
import industries.werwolf.training.layers.presenter.taskmanagement.TaskListView;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;
import industries.werwolf.training.layers.ui.base.ui.component.ProgressDialog;
import industries.werwolf.training.layers.ui.base.ui.component.ViewToolbar;
import industries.werwolf.training.layers.ui.base.ui.view.MainLayoutImpl;
import industries.werwolf.training.layers.ui.util.GridDataProviderAdapter;
import industries.werwolf.training.layers.ui.util.UiUtils;
import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.Nullable;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

@PermitAll
@Route(value = "task-list", layout = MainLayoutImpl.class)
@PageTitle("Task List")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task List")
public class TaskListViewImpl extends Main implements TaskListView {

    private final TaskListPresenter presenter;

    private final ProgressDialog progressDialog = new ProgressDialog().withTitle("Creating Task").withShowPercentage(true);
    private final TextField description = new TextField();
    private final DatePicker dueDate = new DatePicker();
    private final Grid<Task> taskGrid = new Grid<>();

    @Nullable
    private GridDataProviderAdapter<Task, TaskFilter> dataProvider = null;

    public TaskListViewImpl(TaskListPresenter presenter, Clock clock) {
        this.presenter = presenter;

        description.setPlaceholder("What do you want to do?");
        description.setAriaLabel("Task description");
        description.setMaxLength(TaskJpa.DESCRIPTION_MAX_LENGTH);
        description.setMinWidth("20em");

        dueDate.setPlaceholder("Due date");
        dueDate.setAriaLabel("Due date");

        Button createBtn = new Button("Create", event -> presenter.createTaskClicked(description.getValue(), dueDate.getValue()));
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone()).withLocale(getLocale());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);

        DatePicker dateFrom = new DatePicker();
        dateFrom.setPlaceholder("From");
        dateFrom.setClearButtonVisible(true);

        DatePicker dateTo = new DatePicker();
        dateTo.setPlaceholder("To");
        dateTo.setClearButtonVisible(true);

        FlexLayout dateFilter = new FlexLayout(dateFrom, dateTo);
        dateFilter.addClassName(LumoUtility.Gap.SMALL);

        taskGrid.setSizeFull();

        Grid.Column<Task> desc = taskGrid.addColumn(Task::getDescription)
                .setHeader("Description").setSortProperty("description");

        Grid.Column<Task> dueDateColumn = taskGrid.addColumn(task -> Optional.ofNullable(task.getDueDate()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Due Date").setSortProperty("dueDate");

        taskGrid.addColumn(task -> dateTimeFormatter.format(task.getCreationDate()))
                .setHeader("Creation Date").setSortProperty("creationDate");

        HeaderRow headerRow = taskGrid.appendHeaderRow();
        headerRow.getCell(desc).setComponent(searchField);
        headerRow.getCell(dueDateColumn).setComponent(dateFilter);

        searchField.addValueChangeListener(e -> searchTriggered(e.getValue(), dateFrom.getValue(), dateTo.getValue()));
        dateFrom.addValueChangeListener(e -> {
            dateTo.setMin(e.getValue());
            searchTriggered(searchField.getValue(), e.getValue(), dateTo.getValue());
        });
        dateTo.addValueChangeListener(e -> {
            dateFrom.setMax(e.getValue());
            searchTriggered(searchField.getValue(), dateFrom.getValue(), e.getValue());
        });

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Task List", ViewToolbar.group(description, dueDate, createBtn)));
        add(taskGrid);
    }

    private void searchTriggered(String value, @Nullable LocalDate dateFrom, @Nullable LocalDate dateTo) {
        Optional.ofNullable(dataProvider).ifPresent(dp ->
                dp.setFilter(createFilter(value, dateFrom, dateTo)));
    }

    private static TaskFilter createFilter(String value, @Nullable LocalDate dateFrom, @Nullable LocalDate dateTo) {
        return new TaskFilter(value, dateFrom == null && dateTo == null ? null : new Range<>(dateFrom, dateTo));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if (attachEvent.isInitialAttach()) {
            presenter.viewInitialized(this);
        }
    }

    @Override
    public void init(FilterableDataProvider<Task, TaskFilter> tasks) {
        dataProvider = new GridDataProviderAdapter<>(tasks);
        taskGrid.setItems(dataProvider);
    }

    @Override
    public void refreshTaskList() {
        UiUtils.doInUiThread(getUI(), ui -> taskGrid.getDataProvider().refreshAll());
    }

    @Override
    public void clearInputFields() {
        UiUtils.doInUiThread(getUI(), ui -> {
            description.clear();
            dueDate.clear();
        });
    }

    @Override
    public void showTaskAddedNotification() {
        UiUtils.doInUiThread(getUI(), ui ->
                Notification.show("Task added", 3000, Notification.Position.BOTTOM_END)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS));
    }

    @Override
    public void openProgressDialog() {
        if (!progressDialog.isOpened()) {
            progressDialog.open();
        }
    }

    @Override
    public void showProgress(double max, double current) {
        UiUtils.doInUiThread(getUI(), ui -> progressDialog.setProgress(max, current));
    }

    @Override
    public void hideProgress() {
        UiUtils.doInUiThread(getUI(), ui -> progressDialog.close());
    }
}
