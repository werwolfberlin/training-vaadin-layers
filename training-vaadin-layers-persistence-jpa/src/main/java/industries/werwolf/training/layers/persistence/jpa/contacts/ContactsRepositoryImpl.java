package industries.werwolf.training.layers.persistence.jpa.contacts;

import industries.werwolf.training.layers.persistence.contacts.ContactsRepository;
import industries.werwolf.training.layers.persistence.contacts.Person;
import industries.werwolf.training.layers.persistence.filter.GlobalFilter;
import industries.werwolf.training.layers.persistence.jpa.base.AbstractDataProviderRepository;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.TypedQuery;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ContactsRepositoryImpl extends AbstractDataProviderRepository<GlobalFilter> implements ContactsRepository {

    private final ContactsRepositoryJpa repository;

    public ContactsRepositoryImpl(ContactsRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Stream<Person> fetch(int offset, int limit, GlobalFilter filter, List<SortOrder> sortOrders) {
        String sql = "SELECT p FROM Person p JOIN p.address ad JOIN p.phoneNumber pn";
        TypedQuery<PersonJpa> query = createQuery(sql, filter, sortOrders.stream().map(so -> new SortOrder("p." + so.property(), so.direction())).toList(), PersonJpa.class);
        return query.setFirstResult(offset).setMaxResults(limit).getResultStream().map(Person.class::cast);
    }

    @Override
    public long count(GlobalFilter filter) {
        String sql = "SELECT count(distinct p) FROM Person p JOIN p.address ad JOIN p.phoneNumber pn";
        TypedQuery<Long> query = createQuery(sql, filter, null, Long.class);
        return query.getSingleResult();
    }


    @Override
    protected void processFilter(GlobalFilter filter, @NonNull List<String> where, @NonNull Map<String, Object> params) {
        if(filter != null && StringUtils.isNotBlank(filter.getSearchTerm())) {
            where.add("p.firstName LIKE :searchTerm OR p.lastName LIKE :searchTerm");
            params.put("searchTerm", "%" + filter.getSearchTerm() + "%");
        }
    }

    @Override
    public void saveContact(Person person) {
        if(!(person instanceof PersonJpa personJpa)){
            throw new IllegalArgumentException("Person must be an instance of PersonJpa");
        }

        repository.saveAndFlush(personJpa);
    }

    @Override
    public Person createPerson() {
        return new PersonJpa();
    }
}
