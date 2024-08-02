package se.thinkcode.todo.v1;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.TodoService;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTaskControllerIT {
    private TodoService service;
    private final Javalin app = Javalin.create();

    @BeforeEach
    void setUp() {
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        service = new TodoService(repository);

        Routes routes = new Routes();
        CreateTaskController createTaskController = new CreateTaskController(service);
        routes.addHandler(createTaskController);

        routes.routes(app);
    }

    @Test
    public void should_create_task() {
        JavalinTest.test(app, (server, client) -> {
            CreateTaskRequest request = new CreateTaskRequest("Buy cat food");
            try (Response response = client.post("/createTask", request)) {
                assertThat(response.code()).isEqualTo(201);
                assertThat(service.getAllTasks()).hasSize(1);
            }
        });
    }

}
