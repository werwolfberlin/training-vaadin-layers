package industries.werwolf.training.layers.ui.taskmanagement.ui.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.presenter.contacts.ContactEditor;
import industries.werwolf.training.layers.presenter.contacts.ContactEditorPresenter;
import industries.werwolf.training.layers.ui.base.ui.component.AddressEditor;
import industries.werwolf.training.layers.ui.base.ui.component.PhoneNumberField;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ContactEditorImpl extends Div implements ContactEditor {

    private final Binder<Person> binder = new Binder<>();
    private final ContactEditorPresenter presenter;

    public ContactEditorImpl(ContactEditorPresenter presenter) {
        this.presenter = presenter;

        TextField firstName = new TextField("First Name");
        firstName.addClassName(LumoUtility.Flex.AUTO);

        TextField lastName = new TextField("Last Name");
        lastName.addClassName(LumoUtility.Flex.AUTO);

        Div name = new Div(firstName, lastName);
        name.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.MEDIUM);

        // add complex fields for phone number and Address
        // add Revert button to cancel and Save button to save

        Button cancel = new Button("Cancel", event -> presenter.cancelPressed());
        Button save = new Button("Save", event -> presenter.saveClicked());
        Div buttons = new Div(cancel, save);
        buttons.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.MEDIUM, LumoUtility.JustifyContent.END);


        PhoneNumberField phoneNumberField = new PhoneNumberField();
        phoneNumberField.setLabel("Phone Number");

        AddressEditor addressEditor = new AddressEditor();
        addressEditor.setLabel("Address");

        add(name, phoneNumberField, addressEditor, buttons);
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Gap.MEDIUM, LumoUtility.AlignItems.STRETCH, LumoUtility.Padding.MEDIUM);

        binder.forField(firstName)
                .withValidator(new StringLengthValidator("First name must be between 3 and 64 characters long", 3, 64))
                .bind(Person::getFirstName, Person::setFirstName);

        binder.forField(lastName)
                .withValidator(new StringLengthValidator("Last name must be between 3 and 64 characters long", 3, 64))
                .bind(Person::getLastName, Person::setLastName);

        binder.forField(phoneNumberField).bind(Person::getPhoneNumber, Person::setPhoneNumber);
        binder.forField(addressEditor).bind(Person::getAddress, Person::setAddress);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if(attachEvent.isInitialAttach()) {
            presenter.viewInitialized(this);
        }
    }

    public void showPerson(Person person) {
        presenter.personNeedsToBeShown(person);
    }

    @Override
    public void readPerson(Person person) {
        binder.readBean(person);
    }

    @Override
    public void writePerson(Person person) {
        if(binder.isValid()) {
            boolean success = binder.writeBeanIfValid(person);
            if(success) {
                presenter.beanWritten(person);
                return;
            }
        }
        presenter.writeBeanFailed();
    }

    @Override
    public void showWriteSuccess() {
        Notification.show("Contact saved successfully");
    }

    @Override
    public void showWriteError() {
        Notification.show("Error saving contact");
    }

    public void setReadOnly(boolean readOnly) {
        binder.setReadOnly(readOnly);
    }
}
