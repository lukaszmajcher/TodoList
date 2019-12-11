package pl.majchrosoft.ToDoList.tasks.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileSystemStorageService implements StorageService {

    @Value("${storage.path}")
    private String path;

    @Override
    public Path saveFile(Long taskId, MultipartFile file) throws IOException {
        Path targetPath = Path.of(path).resolve(file.getName());
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return targetPath;
    }

    @Override
    public Resource loadFile(String filename) throws MalformedURLException {
        return new UrlResource(Path.of(path).resolve(filename).toUri());
    }
}
