package industries.werwolf.training.layers.ui.taskmanagement.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.binder.Binder;
import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.presenter.contacts.ContactEditor;
import industries.werwolf.training.layers.presenter.contacts.ContactEditorPresenter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ContactEditorImpl extends Div implements ContactEditor {

    private final Binder<Person> binder = new Binder<>();

    public ContactEditorImpl(ContactEditorPresenter presenter) {
        // add fields for first name and last name
        // add complex fields for phone number and Address
        // add Revert button to cancel and Save button to save
    }

    public void showPerson(Person person) {
        binder.readBean(person);
    }
}
