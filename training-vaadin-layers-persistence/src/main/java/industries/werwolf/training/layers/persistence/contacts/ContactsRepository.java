package industries.werwolf.training.layers.persistence.contacts;

import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.persistence.util.SortOrder;

import java.util.List;
import java.util.stream.Stream;

public interface ContactsRepository {
    Stream<Person> fetch(int offset, int limit, GlobalFilter filter, List<SortOrder> sortOrders);
    long count(GlobalFilter filter);
    void saveContact(Person person);
    Person createPerson();
}
