package industries.werwolf.training.layers.presenter.contacts;

import industries.werwolf.training.layers.persistence.contacts.Person;

public interface ContactEditor {
    void readPerson(Person person);

    void writePerson(Person person);

    void showWriteSuccess();

    void showWriteError();
}
