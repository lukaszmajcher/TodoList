package pl.majchrosoft.ToDoList.tasks.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.majchrosoft.ToDoList.Clock;
import pl.majchrosoft.ToDoList.tasks.boundary.StorageService;
import pl.majchrosoft.ToDoList.tasks.boundary.TasksRepository;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;
    private final StorageService storageService;
    private final Clock clock;


    public Task addTask(String title, String description){
        Task task = new Task(
                title,
                description,
                clock.time());
        tasksRepository.add(task);
        return task;
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

    public void addTaskAttachments(Long id, MultipartFile file, String comment) throws IOException {
        Task task = tasksRepository.fetchById(id);
        if (!file.isEmpty()) {
            String filename = storageService.saveFile(id, file);
            task.addAttachment(filename, comment);
            tasksRepository.save(task);
        }
    }
}
