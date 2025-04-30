package industries.werwolf.training.layers.persistence.taskmanagement;

import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDate;

public interface Task {
    String getDescription();

    void setDescription(String description);

    Instant getCreationDate();

    void setCreationDate(Instant creationDate);

    @Nullable
    LocalDate getDueDate();

    void setDueDate(@Nullable LocalDate dueDate);
}
