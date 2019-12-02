package pl.majchrosoft.ToDoList.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Task {
    private long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
