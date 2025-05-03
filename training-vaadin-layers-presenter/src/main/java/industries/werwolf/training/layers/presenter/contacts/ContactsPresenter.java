package industries.werwolf.training.layers.presenter.contacts;

import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.service.contacts.ContactsService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactsPresenter {
    private final ContactsService service;
    private ContactsView view;

    public ContactsPresenter(ContactsService service, ContactUpdatedBroadcaster broadcaster) {
        this.service = service;
        broadcaster.addListener(this::setContacts);
    }

    public void viewInitialized(ContactsView view) {
        this.view = view;
        setContacts();
        view.setEditorReadOnly(true);
    }

    private void setContacts() {
        view.setContacts(service.getContactsDataProvider());
        showPerson(null);
        view.setEditorReadOnly(false);
    }

    public void contactChanged(Person value) {
        showPerson(value);
    }

    public void newButtonClicked() {
        Person person = service.createPerson();
        showPerson(person);
    }

    private void showPerson(Person person) {
        view.setEditorReadOnly(person == null);
        view.showPerson(person);
    }
}
