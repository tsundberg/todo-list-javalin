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
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        TodoService service = new TodoService(repository);

        CreateTaskController createTaskController = new CreateTaskController(service);
        GetAllTasksController getAllTasksController = new GetAllTasksController(service);

        handlers.put(createTaskController.getClass().getCanonicalName(), createTaskController);
        handlers.put(getAllTasksController.getClass().getCanonicalName(), getAllTasksController);
    }

    public void addHandler(Handler handler) {
        String canonicalName = handler.getClass().getCanonicalName();
        canonicalName = canonicalName.substring(0, canonicalName.indexOf("$"));
        handlers.put(canonicalName, handler);
    }

    public void routes(Javalin app) {
        app.post("/createTask", getCreateTaskController());
        app.get("/getAllTasks", getGetAllTasksController());
    }

    public Handler getCreateTaskController() {
        return handlers.get(CreateTaskController.class.getCanonicalName());
    }

    public Handler getGetAllTasksController() {
        return handlers.get(GetAllTasksController.class.getCanonicalName());
    }
}
