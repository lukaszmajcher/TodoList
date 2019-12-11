package pl.majchrosoft.ToDoList.tasks.control;

import org.springframework.stereotype.Service;
import pl.majchrosoft.ToDoList.Clock;
import pl.majchrosoft.ToDoList.tasks.boundary.TasksRepository;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TasksService {

    private final TasksRepository tasksRepository;
    private final Clock clock;

    private final AtomicLong nextTaskId = new AtomicLong(0L);

    public TasksService(TasksRepository tasksRepository, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.clock = clock;
    }

    public void addTask(String title, String description){
        tasksRepository.add(
                new Task(nextTaskId.getAndIncrement(),
                        title,
                        description,
                        clock.time(),
                        new ArrayList<>())
        );
    }

    public void updateTask(Long id, String title, String description) {
        tasksRepository.update(id, title, description);
    }

    public Task fetchById(Long id) {
        return tasksRepository.fetchById(id);
    }

    public List<Task> fetchAll(){
        return tasksRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query){
        return tasksRepository.fetchAll()
                .stream()
                .filter(task -> {
                    return task.getTitle().contains(query) ||
                            task.getDescription().contains(query);
                })
                .collect(Collectors.toList());
    }

    public void deleteTask(Long id) {
        tasksRepository.deleteById(id);
    }
}
