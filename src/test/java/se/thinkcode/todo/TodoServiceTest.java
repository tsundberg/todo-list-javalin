package se.thinkcode.todo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoServiceTest {

    private final TaskRepository repository = new InMemoryTaskRepository();
    private final TodoService service = new TodoService(repository);

    @Test
    void should_create_a_task() {
        Owner owner = new Owner("David");
        Task task = new Task("Buy cat food");

        service.createTask(owner, task);
        List<Task> actual = service.getTasks(owner);

        assertThat(actual).containsExactlyInAnyOrder(task);
    }
}
