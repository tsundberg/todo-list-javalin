package se.thinkcode.todo.v1;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTaskControllerIT {
    private final Javalin app = Javalin.create();
    private CreateTaskController createTaskController;

    @BeforeEach
    void setUp() {
        Routes routes = new Routes();
        createTaskController = mock(CreateTaskController.class);
        routes.addHandler(createTaskController);

        routes.routes(app);
    }

    @Test
    public void should_create_task() {
        JavalinTest.test(app, (server, client) -> {
            try (Response response = client.post("/createTask")) {
                verify(createTaskController).handle(any(Context.class));
            }
        });
    }

}
