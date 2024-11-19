package se.thinkcode.todo;

import java.util.List;

public interface TaskRepository {
    void add(Owner owner, Task task);

    List<Task> getTasks(Owner owner);
}
