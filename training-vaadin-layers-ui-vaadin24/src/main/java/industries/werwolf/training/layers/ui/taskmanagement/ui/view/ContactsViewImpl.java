package industries.werwolf.training.layers.ui.taskmanagement.ui.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.presenter.contacts.ContactsPresenter;
import industries.werwolf.training.layers.presenter.contacts.ContactsView;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;
import industries.werwolf.training.layers.ui.base.ui.component.ViewToolbar;
import industries.werwolf.training.layers.ui.base.ui.view.MainLayoutImpl;
import industries.werwolf.training.layers.ui.util.GridDataProviderAdapter;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;

@PermitAll
@Route(value = "contacts", layout = MainLayoutImpl.class)
@PageTitle("Contacts")
@Menu(order = 1, icon = "vaadin:clipboard-user", title = "Contacts")
public class ContactsViewImpl extends Main implements ContactsView {

    private final Grid<Person> grid = new Grid<>();
    private final ContactsPresenter presenter;
    private final ContactEditorImpl editor;

    @Nullable
    private GridDataProviderAdapter<Person, GlobalFilter> dataProvider;

    public ContactsViewImpl(ContactsPresenter presenter, ContactEditorImpl editor) {
        this.presenter = presenter;
        this.editor = editor;

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);
        searchField.setWidthFull();

        Button buttonNew = new Button("New");
        buttonNew.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        editor.addClassName(LumoUtility.Flex.AUTO);

        grid.addClassName(LumoUtility.Flex.AUTO);
        grid.setHeightFull();
        grid.addColumn(Person::getFirstName)
                .setHeader("First Name");

        grid.addColumn(Person::getLastName)
                .setHeader("Last Name");

        grid.addColumn(p -> Optional.ofNullable(p.getPhoneNumber()).map(pn ->
                Optional.ofNullable(pn.getCountryCode()).map(cc -> "+" + cc).orElse("") +
                        Optional.ofNullable(pn.getAreaCode()).map(ac -> "(" + ac + ")").orElse("") +
                        pn.getNumber()).orElse(""))
                .setHeader("Phone Number");

        Div content = new Div(grid, editor);
        content.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.ROW, LumoUtility.Flex.GROW);

        Component toolBar = ViewToolbar.group(searchField, buttonNew);
        toolBar.addClassName(LumoUtility.Flex.GROW);

        setSizeFull();
        add(new ViewToolbar("Contacts", toolBar), content);
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL, LumoUtility.AlignItems.STRETCH);

        grid.asSingleSelect().addValueChangeListener(e -> presenter.contactChanged(e.getValue()));

        searchField.addValueChangeListener(
                e -> Optional.ofNullable(dataProvider).ifPresent(
                        dp -> dp.setFilter(new GlobalFilter(e.getValue()))));

        buttonNew.addClickListener(e -> presenter.newButtonClicked());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if(attachEvent.isInitialAttach()) {
            presenter.viewInitialized(this);
        }
    }

    @Override
    public void initialize(FilterableDataProvider<Person, GlobalFilter> contactsDataProvider) {
        dataProvider = new GridDataProviderAdapter<>(contactsDataProvider);
        grid.setItems(dataProvider);
    }

    @Override
    public void showPerson(Person person) {
        editor.showPerson(person);
    }
}
