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
import se.thinkcode.todo.TaskRepository;
import se.thinkcode.todo.TodoService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateTaskControllerIT {
    private final Javalin app = Javalin.create();
    private final Routes routes = new Routes();

    @Test
    void should_verify_route_using_a_mock() {
        CreateTaskController createTaskController = mock(CreateTaskController.class);
        routes.overrideController(createTaskController, CreateTaskController.class);
        routes.routes(app);
        JavalinTest.test(app, (server, client) -> {
            Response response = client.post("/createTask");

            verify(createTaskController).handle(any(Context.class));

            response.close();
        });
    }

    @Test
    void should_verify_route_using_a_concrete_implementation() {
        TaskRepository repository = new InMemoryTaskRepository();
        TodoService service = new TodoService(repository);
        CreateTaskController createTaskController = new CreateTaskController(service);

        routes.overrideController(createTaskController, CreateTaskController.class);
        routes.routes(app);

        CreateTaskRequest request = new CreateTaskRequest("Buy cat food");

        JavalinJackson javalinJackson = new JavalinJackson();
        String json = javalinJackson.toJsonString(request, CreateTaskRequest.class);

        JavalinTest.test(app, (server, client) -> {
            try (Response response = client.post("/createTask", json)) {

                List<Task> actual = service.getAllTasks();

                assertThat(response.code()).isEqualTo(201);
                assertThat(actual).containsExactlyInAnyOrder(new Task("Buy cat food"));
            }
        });
    }
}
