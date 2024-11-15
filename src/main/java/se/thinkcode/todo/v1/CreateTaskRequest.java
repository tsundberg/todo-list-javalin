package se.thinkcode.todo.v1;

import se.thinkcode.todo.Owner;
import se.thinkcode.todo.Task;

public record CreateTaskRequest(String name,
                                String task) {

    Owner toOwner() {
        return new Owner(name);
    }

    Task toTask() {
        return new Task(task);
    }
}
