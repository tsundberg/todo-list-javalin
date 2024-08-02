import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoListIT {

    private final TodoList todoList = new TodoList();

    @Test
    void should_create_the_todo_app() {
        assertThat(todoList).isNotNull();
    }
}
