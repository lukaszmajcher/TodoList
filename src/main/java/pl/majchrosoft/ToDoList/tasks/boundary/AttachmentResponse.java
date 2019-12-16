package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.majchrosoft.ToDoList.tasks.entity.Attachment;

@Data
@AllArgsConstructor
public class AttachmentResponse {
    String filename;
    String comment;

    static AttachmentResponse from(Attachment attachment) {
        return new AttachmentResponse(attachment.getFilename(), attachment.getComment());
    }
}
