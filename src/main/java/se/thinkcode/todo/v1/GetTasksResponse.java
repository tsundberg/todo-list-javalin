package se.thinkcode.todo.v1;

import se.thinkcode.todo.Task;

import java.util.ArrayList;
import java.util.List;

public record GetTasksResponse(List<String> tasks) {

    public static GetTasksResponse fromModel(List<Task> tasks) {
        List<String> tasksAsString = new ArrayList<>(tasks.size());

        for (Task task : tasks) {
            tasksAsString.add(task.task());
        }

        return new GetTasksResponse(tasksAsString);
    }
}
