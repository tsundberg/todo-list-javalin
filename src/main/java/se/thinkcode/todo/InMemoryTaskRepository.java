package se.thinkcode.todo;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void createTask(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks;
    }
}
