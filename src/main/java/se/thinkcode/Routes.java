package se.thinkcode;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import se.thinkcode.todo.InMemoryTaskRepository;
import se.thinkcode.todo.TodoService;
import se.thinkcode.todo.v1.CreateTaskController;
import se.thinkcode.todo.v1.GetAllTasksController;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    private final Map<String, Handler> handlers = new HashMap<>();

    public Routes() {
        CreateTaskController createTaskController = new CreateTaskController(getTodoService());
        GetAllTasksController getAllTasksController = new GetAllTasksController(getTodoService());

        handlers.put(createTaskController.getClass().getCanonicalName(), createTaskController);
        handlers.put(getAllTasksController.getClass().getCanonicalName(), getAllTasksController);
    }

    private TodoService getTodoService() {
        InMemoryTaskRepository repository = getInMemoryTaskRepository();
        return new TodoService(repository);
    }

    private InMemoryTaskRepository getInMemoryTaskRepository() {
        return new InMemoryTaskRepository();
    }

    public void overrideController(Handler handler, Class<? extends Handler> controller) {
        String canonicalName = controller.getCanonicalName();
        handlers.put(canonicalName, handler);
    }

    public void routes(Javalin app) {
        app.post("/createTask", getCreateTaskController());
        app.get("/getAllTasks", getGetAllTasksController());
    }

    private Handler getCreateTaskController() {
        return handlers.get(CreateTaskController.class.getCanonicalName());
    }

    private Handler getGetAllTasksController() {
        return handlers.get(GetAllTasksController.class.getCanonicalName());
    }
}
