package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.majchrosoft.ToDoList.tasks.control.TasksService;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {

    private final TasksRepository tasksRepository;
    private final TasksService tasksService;

    @PostConstruct
    void init() {
        tasksService.addTask("Dokonczyc zadanie z modulu 1", "Rejestracja na FB");
        tasksService.addTask("Obejrzec modul 2", "Wprowadzenie do Springa");
        tasksService.addTask("Stworzyc wlasny projekt na Githubie", "https://github.com");
    }

    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam Optional<String> query) {
        log.info("Fetching all tasks with filter: {} ", query);
        return query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("Fetching all task with id: {}", id);
        return toTaskResponse(tasksRepository.fetchById(id));
    }

    @GetMapping(path = "/hello")
    public String hello() {
        log.info("Fetching all tasks ...");
        return "tasksRepository.fetchAll()";
    }

    @PostMapping
    public void addTask(@RequestBody CreateTaskRequest task) {
        log.info("Storing new task: {}", task);
        tasksService.addTask(task.title, task.description);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Delete task");
        tasksRepository.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        log.info("Update task");
        tasksService.updateTask(id, request.title, request.description);
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt()
        );
    }
}
