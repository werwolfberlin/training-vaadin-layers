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
    private Person person;

    public ContactEditorPresenter(ContactsService service, ContactUpdatedBroadcaster broadcaster) {
        this.service = service;
        this.broadcaster = broadcaster;
    }

    public void viewInitialized(ContactEditor view) {
        this.view = view;
    }

    public void saveClicked() {
        view.writePerson(this.person);
    }

    public void cancelPressed() {
        view.readPerson(person);
    }

    public void personNeedsToBeShown(Person person) {
        this.person = person;
        view.readPerson(person);
    }

    public void beanWritten(Person person) {
        try {
            service.saveContact(person);
            broadcaster.contactUpdated();
            view.showWriteSuccess();
        }
        catch (Exception e) {
            e.printStackTrace();
            view.showWriteError();
        }
    }

    public void writeBeanFailed() {
        view.showWriteError();
    }
}
