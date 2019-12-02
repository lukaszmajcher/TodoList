package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequest {
    String title;
    String description;
    LocalDateTime createdAt;
}
