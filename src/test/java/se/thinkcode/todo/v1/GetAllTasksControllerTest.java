package se.thinkcode.todo.v1;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Test;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.TaskRepository;
import se.thinkcode.todo.TodoService;

import static org.mockito.Mockito.*;

public class GetAllTasksControllerTest {

    private final TaskRepository repository = new InMemoryTaskRepository();
    private final TodoService service = new TodoService(repository);

    private final Context ctx = mock(Context.class);
    private final GetAllTasksController controller = new GetAllTasksController(service);

    @Test
    void should_create_task() {
        controller.handle(ctx);

        verify(ctx).json(any(GetTasksResponse.class), eq(GetTasksResponse.class));
        verify(ctx).status(HttpStatus.OK);
    }
}
