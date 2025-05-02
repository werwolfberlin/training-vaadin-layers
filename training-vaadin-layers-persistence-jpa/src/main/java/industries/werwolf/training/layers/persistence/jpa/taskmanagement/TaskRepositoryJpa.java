package industries.werwolf.training.layers.persistence.jpa.taskmanagement;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepositoryJpa extends JpaRepository<TaskJpa, Long>, JpaSpecificationExecutor<TaskJpa> {

    // If you don't need a total row count, Slice is better than Page.
    Slice<TaskJpa> findAllBy(Pageable pageable);
}
