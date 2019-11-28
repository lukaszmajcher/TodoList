package pl.majchrosoft.ToDoList.tasks.boundary;

import pl.majchrosoft.ToDoList.tasks.entity.Task;

import java.util.List;

public interface TasksRepository {

    void add(Task task);

    List<Task> fetchAll();

    Task fetchById(Long id);

    void deleteById(Long id);
}
