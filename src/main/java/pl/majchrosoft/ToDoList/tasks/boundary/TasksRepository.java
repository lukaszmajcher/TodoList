package pl.majchrosoft.ToDoList.tasks.boundary;

import pl.majchrosoft.ToDoList.tasks.entity.Task;

import java.util.List;

public interface TasksRepository {

    List<Task> fetchAll();

    Task fetchById(Long id);

    void add(Task task);

    void update(Long id, String title, String description);

    void deleteById(Long id);

}
