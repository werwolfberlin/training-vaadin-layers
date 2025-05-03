package industries.werwolf.training.layers.presenter.contacts;

import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.service.contacts.ContactsService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContactEditorPresenter {
    private final ContactsService service;
    private final ContactUpdatedBroadcaster broadcaster;
    private ContactEditor view;

    public ContactEditorPresenter(ContactsService service, ContactUpdatedBroadcaster broadcaster) {
        this.service = service;
        this.broadcaster = broadcaster;
    }

    public void viewInitialized(ContactEditor view) {
        this.view = view;
    }

    public void saveClicked(Person person) {
        service.saveContact(person);
        broadcaster.contactUpdated();
    }
}
