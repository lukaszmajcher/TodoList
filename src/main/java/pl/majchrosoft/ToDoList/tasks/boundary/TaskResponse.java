package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    Long id;
    String title;
    String description;
}
