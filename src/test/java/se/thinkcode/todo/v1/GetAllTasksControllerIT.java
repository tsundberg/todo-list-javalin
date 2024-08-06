package se.thinkcode.todo.v1;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.json.JavalinJackson;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GetAllTasksControllerIT {
    private final JavalinJackson javalinJackson = new JavalinJackson();
    private final Javalin app = Javalin.create();
    private final Routes routes = new Routes();

    @Test
    void should_verify_route_using_a_mock() {
        GetAllTasksController getAllTasksController = mock(GetAllTasksController.class);
        routes.overrideController(getAllTasksController, GetAllTasksController.class);
        routes.routes(app);

        JavalinTest.test(app, (server, client) -> {
            client.get("/getAllTasks");
            
            verify(getAllTasksController).handle(any(Context.class));
        });
    }

    @Test
    void should_verify_route_using_a_concrete_implementation() {
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        TodoService service = new TodoService(repository);

        GetAllTasksController getAllTasksController = new GetAllTasksController(service);
        routes.overrideController(getAllTasksController, GetAllTasksController.class);

        routes.routes(app);

        GetTasksResponse expected = new GetTasksResponse(List.of("Buy cat food"));
        Task task = new Task("Buy cat food");
        service.createTask(task);

        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/getAllTasks");

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body()).isNotNull();

            GetTasksResponse actual = javalinJackson.fromJsonStream(response.body().byteStream(), GetTasksResponse.class);
            assertThat(actual).isEqualTo(expected);
        });
    }
}
