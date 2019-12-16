package pl.majchrosoft.ToDoList.tags.boundary;

import org.springframework.data.repository.CrudRepository;
import pl.majchrosoft.ToDoList.tags.entity.Tag;

public interface TagsCrudRepository extends CrudRepository<Tag, Long> {
}
