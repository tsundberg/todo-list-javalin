package se.thinkcode;

import io.javalin.Javalin;
import se.thinkcode.todo.v1.CreateTaskController;
import se.thinkcode.todo.v1.GetAllTasksController;

public class Routes {

    private final CreateTaskController createTaskController;
    private final GetAllTasksController getAllTasksController;

    public Routes(CreateTaskController createTaskController,
                  GetAllTasksController getAllTasksController) {
        this.createTaskController = createTaskController;
        this.getAllTasksController = getAllTasksController;
    }

    public void routes(Javalin app) {
        app.post("/createTask", getCreateTaskController());
        app.get("/getAllTasks", getGetAllTasksController());
    }

    public CreateTaskController getCreateTaskController() {
        return createTaskController;
    }

    public GetAllTasksController getGetAllTasksController() {
        return getAllTasksController;
    }
}
