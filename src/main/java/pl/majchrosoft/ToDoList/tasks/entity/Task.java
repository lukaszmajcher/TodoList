package pl.majchrosoft.ToDoList.tasks.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pl.majchrosoft.ToDoList.tags.entity.Tag;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Data
@Table("task")
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Set<Attachment> attachments;
    private Set<TagRef> tagRefs;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public void addAttachment(String filename, String comment) {
        attachments.add(new Attachment(filename, comment));
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void addTag(Tag tag) {
        tagRefs.add(new TagRef(tag));
    }

    public void removeTag(Tag tag) {
        tagRefs.remove(new TagRef(tag));
    }
}
