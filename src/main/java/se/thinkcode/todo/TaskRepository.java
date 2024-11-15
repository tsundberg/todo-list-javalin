package se.thinkcode.todo;

import java.util.List;

public interface TaskRepository {
    void createTask(Owner owner, Task task);

    List<Task> getTasks(Owner owner);
}
