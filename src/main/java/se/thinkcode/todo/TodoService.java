package se.thinkcode.todo;

import java.util.List;

public class TodoService {

    private final TaskRepository repository;

    public TodoService(TaskRepository repository) {
        this.repository = repository;
    }

    public void createTask(Task task) {
        repository.createTask(task);
    }

    public List<Task> getAllTasks() {
        return repository.getAllTasks();
    }
}
