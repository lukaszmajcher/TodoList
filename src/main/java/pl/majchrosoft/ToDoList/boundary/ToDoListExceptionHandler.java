package pl.majchrosoft.ToDoList.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.majchrosoft.ToDoList.exceptions.NotFoundException;

@ControllerAdvice
public class ToDoListExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity notFoundHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }


}
