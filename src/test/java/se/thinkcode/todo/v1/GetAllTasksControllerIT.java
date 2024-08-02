package se.thinkcode.todo.v1;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllTasksControllerIT {
    private final Javalin app = Javalin.create();
    private TodoService service;

    @BeforeEach
    void setUp() {
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        service = new TodoService(repository);

        CreateTaskController createTaskController = new CreateTaskController(service);
        GetAllTasksController getAllTasksController = new GetAllTasksController(service);

        Routes routes = new Routes(createTaskController, getAllTasksController);
        routes.routes(app);
    }

    @Test
    public void should_get_all_tasks() {
        Task task = new Task("Buy cat food");
        service.createTask(task);

        GetTasksResponse expectedResponse = GetTasksResponse.fromModel(List.of(new Task("Buy cat food")));
        String expected = new JavalinJackson().toJsonString(expectedResponse, GetTasksResponse.class);

        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/getAllTasks");
            assertThat(response.code()).isEqualTo(200);
            // todo serialize the response into the expected object
            assertThat(response.body()).isNotNull();
            assertThat(response.body().string()).isEqualTo(expected);
        });
    }
}
