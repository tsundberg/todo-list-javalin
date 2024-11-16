package se.thinkcode.todo.v1;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import se.thinkcode.todo.Owner;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

public class CreateTaskController implements Handler {
    private final TodoService service;

    public CreateTaskController(TodoService service) {
        this.service = service;
    }

    public void handle(Context ctx) {
        CreateTaskRequest request = ctx.bodyAsClass(CreateTaskRequest.class);

        Owner owner = request.toOwner();
        Task task = request.toTask();
        service.createTask(owner, task);

        ctx.status(HttpStatus.CREATED);
    }
}
