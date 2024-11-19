package se.thinkcode;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.TodoService;
import se.thinkcode.todo.v1.GetTasksController;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    private final Map<String, Handler> overriden = new HashMap<>();

    public void routes(Javalin app) {
        app.post("/v1/createTask", getCreateTaskControllerV1());
        app.get("/v1/getAllTasks/{owner}", getGetAllTasksControllerV1());

        app.post("/v2/createTask", getCreateTaskControllerV2());
        app.get("/v2/getAllTasks/{owner}", getGetAllTasksControllerV2());
    }

    /**
     * Add any controller you want to override in a test here,
     * and it will be used instead of the default one.
     */
    public void overrideController(Handler controller, Class<? extends Handler> controllerClass) {
        String canonicalName = controllerClass.getCanonicalName();

        overriden.put(canonicalName, controller);
    }

    private TodoService getTodoService() {
        InMemoryTaskRepository repository = getInMemoryTaskRepository();

        return new TodoService(repository);
    }

    private InMemoryTaskRepository getInMemoryTaskRepository() {
        return new InMemoryTaskRepository();
    }

    private Handler getCreateTaskControllerV1() {
        String key = se.thinkcode.todo.v1.CreateTaskController.class.getCanonicalName();
        return overriden.getOrDefault(key, new se.thinkcode.todo.v1.CreateTaskController(getTodoService()));
    }

    private Handler getGetAllTasksControllerV1() {
        String key = GetTasksController.class.getCanonicalName();
        return overriden.getOrDefault(key, new GetTasksController(getTodoService()));
    }

    private Handler getCreateTaskControllerV2() {
        String key = se.thinkcode.todo.v2.CreateTaskController.class.getCanonicalName();
        return overriden.getOrDefault(key, new se.thinkcode.todo.v2.CreateTaskController(getTodoService()));
    }

    private Handler getGetAllTasksControllerV2() {
        String key = se.thinkcode.todo.v2.GetTasksController.class.getCanonicalName();
        return overriden.getOrDefault(key, new se.thinkcode.todo.v2.GetTasksController(getTodoService()));
    }
}
