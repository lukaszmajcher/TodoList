package pl.majchrosoft.ToDoList.tasks.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Table("tasks")
public class Task {

    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    //    private List<String> files;


    public Set<String> getFiles() {
        return new HashSet<>();
    }
}
