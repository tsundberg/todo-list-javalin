package se.thinkcode.todo.v1;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.TaskRepository;
import se.thinkcode.todo.TodoService;

import static org.mockito.Mockito.*;

public class GetTasksControllerTest {

    private final TaskRepository repository = new InMemoryTaskRepository();
    private final TodoService service = new TodoService(repository);

    private final Context ctx = mock(Context.class);
    private final GetTasksController controller = new GetTasksController(service);

    @Test
    void should_get_tasks_using_a_mock() {
        controller.handle(ctx);

        verify(ctx).status(HttpStatus.OK);
        verify(ctx).json(ArgumentMatchers.<GetTasksResponse>anyList(), eq(GetTasksResponse.class));
    }
}
