package se.thinkcode.todo.v1;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllTasksControllerIT {
    private final JavalinJackson javalinJackson = new JavalinJackson();
    private final Javalin app = Javalin.create();
    private TodoService service;

    @BeforeEach
    void setUp() {
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        service = new TodoService(repository);

        Routes routes = new Routes();
        GetAllTasksController getAllTasksController = new GetAllTasksController(service);
        routes.overideController(getAllTasksController, GetAllTasksController.class);

        routes.routes(app);
    }

    @Test
    @Disabled
    void should_verify_route_using_a_mock() {
        // todo implement me
        throw new RuntimeException("Not yet implemented");
    }


    @Test
    void should_verify_route_using_a_concrete_implementation() {
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
