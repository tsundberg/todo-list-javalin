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
    private final Map<String, Handler> overriden = new HashMap<>();

    public Routes() {
    }

    public void routes(Javalin app) {
        app.post("/createTask", getCreateTaskController());
        app.get("/getAllTasks", getGetAllTasksController());
    }

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

    private Handler getCreateTaskController() {
        String key = CreateTaskController.class.getCanonicalName();
        if (overriden.containsKey(key)) {
            return overriden.get(key);    
        }     
        
        return new CreateTaskController(getTodoService());
    }

    private Handler getGetAllTasksController() {
        String key = GetAllTasksController.class.getCanonicalName();
        if (overriden.containsKey(key)) {
            return overriden.get(key);    
        }     

        return new GetAllTasksController(getTodoService());
    }
}
