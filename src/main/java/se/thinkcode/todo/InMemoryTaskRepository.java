package se.thinkcode.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Owner, List<Task>> tasks = new HashMap<>();

    @Override
    public void createTask(Owner owner, Task task) {
        List<Task> taskLs = tasks.getOrDefault(owner, new ArrayList<>());
        taskLs.add(task);
        tasks.put(owner, taskLs);
    }

    @Override
    public List<Task> getTasks(Owner owner) {
        return tasks.getOrDefault(owner, new ArrayList<>());
    }
}
