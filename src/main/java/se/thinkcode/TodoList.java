package se.thinkcode;

import io.javalin.Javalin;
import se.thinkcode.todo.v1.CreateTaskController;
import se.thinkcode.todo.v1.GetAllTasksController;

public class TodoList {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000); // create and launch server

        CreateTaskController createTaskController = new CreateTaskController(null);
        GetAllTasksController getAllTasksController = new GetAllTasksController(null);

        Routes routes = new Routes(createTaskController, getAllTasksController);
        routes.routes(app);
    }
}
