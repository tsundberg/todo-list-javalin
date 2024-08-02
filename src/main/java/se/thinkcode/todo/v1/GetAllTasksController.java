package se.thinkcode.todo.v1;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

import java.util.List;

public class GetAllTasksController implements Handler {
    private final TodoService service;

    public GetAllTasksController(TodoService service) {
        this.service = service;
    }

    @Override
    public void handle(Context ctx) {
        List<Task> tasks = service.getAllTasks();
        GetTasksResponse response = GetTasksResponse.fromModel(tasks);

        ctx.json(response, GetTasksResponse.class);
        ctx.status(HttpStatus.OK);
    }
}
