package pl.majchrosoft.ToDoList.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Task {
    private long id;
    private String title;
    private String description;
}
