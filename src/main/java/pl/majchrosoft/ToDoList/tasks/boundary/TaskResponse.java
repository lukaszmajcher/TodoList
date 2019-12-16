package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.majchrosoft.ToDoList.tasks.entity.Attachment;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class TaskResponse {
    Long id;
    String title;
    String description;
    LocalDateTime createdAt;
    Set<String> attachments;
}
