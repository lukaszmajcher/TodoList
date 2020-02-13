package pl.majchrosoft.ToDoList.tags.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("tag")
public class Tag {
    @Id
    private Long id;
    private String title;
}
