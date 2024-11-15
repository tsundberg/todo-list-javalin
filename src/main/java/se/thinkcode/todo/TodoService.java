package se.thinkcode.todo;

import java.util.List;

public class TodoService {

    private final TaskRepository repository;

    public TodoService(TaskRepository repository) {
        this.repository = repository;
    }

    public void createTask(Owner owner, Task task) {
        repository.createTask(owner, task);
    }

    public List<Task> getTasks(Owner owner) {
        return repository.getTasks(owner);
    }
}
