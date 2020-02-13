package pl.majchrosoft.ToDoList.tags.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.majchrosoft.ToDoList.exceptions.NotFoundException;
import pl.majchrosoft.ToDoList.tags.boundary.TagsCrudRepository;
import pl.majchrosoft.ToDoList.tags.entity.Tag;

@Service
@RequiredArgsConstructor
public class TagsService {

    private final TagsCrudRepository repository;

    public Tag findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag Not found for id: " + id));
    }

}
