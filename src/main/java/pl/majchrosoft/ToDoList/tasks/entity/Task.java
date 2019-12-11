package pl.majchrosoft.ToDoList.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Task {
    private long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<String> files;
}
