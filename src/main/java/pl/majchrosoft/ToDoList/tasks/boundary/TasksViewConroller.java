package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.majchrosoft.ToDoList.tasks.control.TasksService;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TasksViewConroller {

    private final TasksService tasksService;
    private final StorageService storageService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") CreateTaskRequest request,
                          @RequestParam("attachment") MultipartFile attachment) throws IOException {
        Task task = tasksService.addTask(request.title, request.description);
        storageService.saveFile(task.getId(), attachment);
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
        return "redirect:/";
    }
}
