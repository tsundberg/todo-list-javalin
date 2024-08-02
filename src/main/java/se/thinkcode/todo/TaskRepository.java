package se.thinkcode.todo;

import java.util.List;

public interface TaskRepository {
    void createTask(Task task);

    List<Task> getAllTasks();
}
