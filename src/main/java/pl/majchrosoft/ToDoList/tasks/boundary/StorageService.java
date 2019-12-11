package pl.majchrosoft.ToDoList.tasks.boundary;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface StorageService {

    Path saveFile(Long taskId, MultipartFile file) throws IOException;
    Resource loadFile(String filename) throws MalformedURLException;
}
