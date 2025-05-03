package industries.werwolf.training.layers.presenter.contacts;

import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;

public interface ContactsView {
    void setContacts(FilterableDataProvider<Person, GlobalFilter> contactsDataProvider);

    void showPerson(Person person);

    void setEditorReadOnly(boolean readOnly);
}
