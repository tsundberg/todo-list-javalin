package se.thinkcode.todo.v1;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateTaskControllerIT {
    private final Javalin app = Javalin.create();
    private CreateTaskController createTaskController;

    @BeforeEach
    void setUp() {
        Routes routes = new Routes();
        createTaskController = mock(CreateTaskController.class);
        routes.overideController(createTaskController, CreateTaskController.class);

        routes.routes(app);
    }

    @Test
    void should_verify_route_using_a_mock() {
        JavalinTest.test(app, (server, client) -> {
            try (Response response = client.post("/createTask")) {
                verify(createTaskController).handle(any(Context.class));
            }
        });
    }

    @Test
    @Disabled
    void should_verify_route_using_a_concrete_implementation() {
        // todo implement me
        throw new RuntimeException("Not yet implemented");
    }
}
