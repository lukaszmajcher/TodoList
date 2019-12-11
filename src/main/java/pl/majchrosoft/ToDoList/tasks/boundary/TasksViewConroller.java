package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.majchrosoft.ToDoList.tasks.control.TasksService;

@Controller
@RequiredArgsConstructor
public class TasksViewConroller {

    private final TasksService tasksService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll());
        return "home";
    }
}
