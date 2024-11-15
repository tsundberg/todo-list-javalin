package se.thinkcode.todo.v1;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import se.thinkcode.todo.Owner;
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
        String name = ctx.pathParam("owner");
        Owner owner = new Owner(name);
        List<Task> tasks = service.getTasks(owner);
        GetTasksResponse response = GetTasksResponse.fromModel(tasks);

        ctx.json(response, GetTasksResponse.class);
        ctx.status(HttpStatus.OK);
    }
}
