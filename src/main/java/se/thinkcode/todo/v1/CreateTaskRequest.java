package se.thinkcode.todo.v1;

import se.thinkcode.todo.Task;

public record CreateTaskRequest(String task) {
    public Task toModel() {
        return new Task(task());
    }
}
