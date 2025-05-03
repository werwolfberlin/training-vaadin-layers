package industries.werwolf.training.layers.service.contacts;

import industries.werwolf.training.layers.persistence.contacts.ContactsRepository;
import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import industries.werwolf.training.layers.service.util.FilterableDataProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ContactsService {

    private final ContactsRepository repository;

    public ContactsService(ContactsRepository repository) {
        this.repository = repository;
    }

    public FilterableDataProvider<Person, GlobalFilter> getContactsDataProvider() {
        return new FilterableDataProvider<>() {
            @Override
            public Stream<Person> fetch(int offset, int limit, GlobalFilter filter, List<SortOrder> sortOrders) {
                return repository.fetch(offset, limit, filter, sortOrders);
            }

            @Override
            public long count(GlobalFilter filter) {
                return repository.count(filter);
            }
        };
    }

    public Person createPerson() {
        return repository.createPerson();
    }

    public void saveContact(Person person) {
        repository.saveContact(person);
    }
}
