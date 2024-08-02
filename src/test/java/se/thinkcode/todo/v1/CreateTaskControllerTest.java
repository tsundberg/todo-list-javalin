package se.thinkcode.todo.v1;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Test;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.TaskRepository;
import se.thinkcode.todo.TodoService;

import static org.mockito.Mockito.*;

class CreateTaskControllerTest {
    private final TaskRepository repository = new InMemoryTaskRepository();
    private final TodoService service = new TodoService(repository);

    private final Context ctx = mock(Context.class);
    private final CreateTaskController controller = new CreateTaskController(service);

    @Test
    void should_create_task() {
        when(ctx.bodyAsClass(CreateTaskRequest.class)).thenReturn(new CreateTaskRequest("Buy cat food"));
        controller.handle(ctx);

        verify(ctx).status(HttpStatus.CREATED);
    }
}
