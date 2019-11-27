package pl.majchrosoft.ToDoList.tasks;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class MemoryTasksRepository implements TasksRepository {

    private final Set<Task> tasks = new HashSet<>();

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> fetchAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Task getchById(Long id) {
        return tasks.stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Override
    public void deleteById(Long id) {
        tasks.stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst()
                .ifPresent(tasks::remove);
    }
}
