package se.thinkcode.todo.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.json.JavalinJackson;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.Owner;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GetTasksControllerIT {
    private final JavalinJackson javalinJackson = new JavalinJackson();
    private final Javalin app = Javalin.create();
    private final Routes routes = new Routes();

    @Test
    void should_verify_route_using_a_mock() {
        GetTasksController getTasksController = mock(GetTasksController.class);
        routes.overrideController(getTasksController, GetTasksController.class);
        routes.routes(app);

        JavalinTest.test(app, (server, client) -> {
            client.get("/v1/getAllTasks/Kalle");

            verify(getTasksController).handle(any(Context.class));
        });
    }

    @Test
    void should_verify_route_using_a_concrete_implementation() {
        ObjectMapper mapper = javalinJackson.getMapper();
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        TodoService service = new TodoService(repository);

        GetTasksController getTasksController = new GetTasksController(service);
        routes.overrideController(getTasksController, GetTasksController.class);

        routes.routes(app);

        GetTasksResponse expected = new GetTasksResponse("Buy cat food");
        Owner owner = new Owner("Kalle");
        Task task = new Task("Buy cat food");
        service.createTask(owner, task);

        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/v1/getAllTasks/Kalle");

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body()).isNotNull();

            List<GetTasksResponse> actual = mapper.readerForListOf(GetTasksResponse.class)
                    .readValue(response.body().byteStream());

            assertThat(actual).containsExactly(expected);
        });
    }
}
