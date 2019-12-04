package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.majchrosoft.ToDoList.exceptions.NotFoundException;
import pl.majchrosoft.ToDoList.tasks.control.TasksService;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {

    private final StorageService storageService;
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

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(
            @PathVariable Long id,
            @PathVariable String filename,
            HttpServletRequest request) throws IOException {
        // pobierać plik
        Resource resource = storageService.loadFile(filename);
        String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (mimeType == null)
            mimeType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Hendling file upload: {}", file.getName());
        storageService.saveFile(id, file);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public void addTask(@RequestBody CreateTaskRequest task) {
        log.info("Storing new task: {}", task);
        tasksService.addTask(task.title, task.description);
        // 201
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Delete task");
        tasksRepository.deleteById(id);
        // 204
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(HttpServletResponse response,
                           @PathVariable Long id,
                           @RequestBody UpdateTaskRequest request) throws IOException {
        // Gdy nie ma ExceptionHendlera, są 3 sposoby
//        try {
//            tasksService.updateTask(id, request.title, request.description);
//        } catch (NotFoundException e) {
//            log.error("Failed to update task", e);
//            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
//        }

//        try {
//            tasksService.updateTask(id, request.title, request.description);
//        } catch (NotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }

        //Gdy jest globalny Exception Handler
//        tasksService.updateTask(id, request.title, request.description);

        //Ostatni sposób to pełna obsługa ResponseEntity
        try {
            tasksService.updateTask(id, request.title, request.description);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Failed to update task", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
                task.getCreatedAt()
        );
    }
}
