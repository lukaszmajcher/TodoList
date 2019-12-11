package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.majchrosoft.ToDoList.tasks.control.TasksService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TasksViewConroller {

    private final TasksService tasksService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") CreateTaskRequest request) {
        log.info("Dodaje zadanie ...");
        tasksService.addTask(request.title, request.description);
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
        return "redirect:/";
    }
}
