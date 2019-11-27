package pl.majchrosoft.ToDoList.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
public class TasksController {

    private final TasksRepository tasksRepository;

    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public List<Task> getTasks() {
        log.info("Fetching all tasks ...");
        return tasksRepository.fetchAll();
    }

    @GetMapping(path = "/{id}")
    public Task getTaskById(@PathVariable Long id) {
        log.info("Fetching all task with id: {}", id);
        return tasksRepository.getchById(id);
    }

    @GetMapping(path = "/hello")
    public String hello() {
        log.info("Fetching all tasks ...");
        return "tasksRepository.fetchAll()";
    }

    @PostMapping
    public void addTask(@RequestBody Task task) {
        log.info("Storing new task: {}", task);
        tasksRepository.add(task);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Delete task");
        tasksRepository.deleteById(id);
    }

    @PutMapping
    public void updateTask() {
        log.info("Update task");
    }
}
