package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.majchrosoft.ToDoList.exceptions.NotFoundException;
import pl.majchrosoft.ToDoList.tasks.control.TasksService;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {

    private final StorageService storageService;
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
    public ResponseEntity getTaskById(@PathVariable Long id) {
        log.info("Fetching all task with id: {}", id);
        try {
            TaskResponse taskResponse = toTaskResponse(tasksService.fetchById(id));
            return ResponseEntity.ok(taskResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity addTask(@RequestBody CreateTaskRequest task) {
        log.info("Storing new task: {}", task);
        tasksService.addTask(task.title, task.description);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask( @PathVariable Long id,
                                     @RequestBody UpdateTaskRequest request) {
        try {
            tasksService.updateTask(id, request.title, request.description);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Failed to update task", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Delete task");
        tasksService.deleteTask(id);
        // 204
    }



    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(
            @PathVariable Long id,
            @PathVariable String filename,
            HttpServletRequest request) throws IOException {

        Resource resource = storageService.loadFile(filename);
        String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (mimeType == null)
            mimeType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Hendling file upload: {}", file.getName());
        Task task = tasksService.fetchById(id);
        Path path = storageService.saveFile(id, file);
        task.getFiles().add(path.toString());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/hello")
    public String hello() {
        log.info("Fetching all tasks ...");
        return "tasksRepository.fetchAll()";
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getFiles()
        );
    }
}
