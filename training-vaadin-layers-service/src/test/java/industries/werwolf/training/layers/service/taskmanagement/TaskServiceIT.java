package industries.werwolf.training.layers.service.taskmanagement;

import industries.werwolf.training.layers.persistence.jpa.taskmanagement.TaskJpa;
import industries.werwolf.training.layers.persistence.jpa.taskmanagement.TaskRepositoryJpa;
import industries.werwolf.training.layers.persistence.util.SortOrder;
import industries.werwolf.training.layers.service.TestConfig;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes=TestConfig.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class TaskServiceIT {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepositoryJpa taskRepository;

    @Autowired
    Clock clock;

    @AfterEach
    void cleanUp() {
        taskRepository.deleteAll();
    }

    @Test
    public void tasks_are_stored_in_the_database_with_the_current_timestamp() {
        var now = clock.instant();
        var due = LocalDate.of(2025, 2, 7);
        taskService.createTask("Do this", due);
        List<SortOrder> sortOrders = List.of(new SortOrder("creationDate", SortOrder.Direction.DESC));
        assertThat(taskService.getTaskListDataProvider().fetch(0, 1, null, sortOrders)).singleElement()
                .matches(task -> task.getDescription().equals("Do this") && due.equals(task.getDueDate())
                        && task.getCreationDate().isAfter(now));
    }

    @Test
    public void tasks_are_validated_before_they_are_stored() {
        assertThatThrownBy(() -> taskService.createTask("X".repeat(TaskJpa.DESCRIPTION_MAX_LENGTH + 1), null))
                .isInstanceOf(ValidationException.class);
        assertThat(taskRepository.count()).isEqualTo(0);
    }
}
