package industries.werwolf.training.layers.persistence.jpa.contacts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactsRepositoryJpa extends JpaRepository<PersonJpa, Long>, JpaSpecificationExecutor<PersonJpa> {
}
