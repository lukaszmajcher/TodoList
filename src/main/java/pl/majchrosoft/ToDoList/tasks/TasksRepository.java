package pl.majchrosoft.ToDoList.tasks;

import java.util.List;

public interface TasksRepository {

    void add(Task task);

    List<Task> fetchAll();

    Task getchById(Long id);

    void deleteById(Long id);
}
