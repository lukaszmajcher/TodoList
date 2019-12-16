package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.majchrosoft.ToDoList.tasks.entity.Attachment;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
public class TaskResponse {
    Long id;
    String title;
    String description;
    LocalDateTime createdAt;
    Set<AttachmentResponse> attachments;

    static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getAttachments()
                        .stream()
                        .map(AttachmentResponse::from)
                        .collect(toSet())
        );
    }
}
