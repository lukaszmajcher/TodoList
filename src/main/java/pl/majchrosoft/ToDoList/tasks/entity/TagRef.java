package pl.majchrosoft.ToDoList.tasks.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import pl.majchrosoft.ToDoList.tags.entity.Tag;

@Data
@Table("tag_task")
public class TagRef {
    private Long tag;

    public TagRef() { }

    public TagRef(Tag tag) {
        this.tag = tag.getId();
    }
}
