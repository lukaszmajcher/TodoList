package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.majchrosoft.ToDoList.exceptions.NotFoundException;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Primary
@Repository
@RequiredArgsConstructor
public class AdapteTasksCrudRepository implements TasksRepository {

    private final TasksCrudRepository repository;

    @Override
    public List<Task> fetchAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Task fetchById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find task with id: " + id));
    }

    @Override
    public void add(Task task) {
        repository.save(task);
    }

    @Override
    public void update(Long id, String title, String description) {
        repository.findById(id)
                .map(task -> {
                    task.setTitle(title);
                    task.setDescription(description);
                    return task;
                })
                .ifPresent(repository::save);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
