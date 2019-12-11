package pl.majchrosoft.ToDoList.tasks.boundary;

import org.springframework.data.repository.CrudRepository;
import pl.majchrosoft.ToDoList.tasks.entity.Task;

interface TasksCrudRepository extends CrudRepository<Task, Long> {

}
