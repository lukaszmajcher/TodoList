package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskResponse {
    Long id;
    String title;
    String description;
    LocalDateTime createdAt;
//    List<String> files;
}
