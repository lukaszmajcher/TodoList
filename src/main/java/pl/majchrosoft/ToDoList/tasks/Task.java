package pl.majchrosoft.ToDoList.tasks;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {

    private String title;
    private String author;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate deadline;
}
