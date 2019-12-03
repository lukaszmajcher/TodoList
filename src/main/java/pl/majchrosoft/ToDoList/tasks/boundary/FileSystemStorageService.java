package pl.majchrosoft.ToDoList.tasks.boundary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileSystemStorageService implements StorageService {

    private static final Path PATH = Path.of("D:\\aaa");

    @Override
    public void saveFile(Long taskId, MultipartFile file) throws IOException {

        Path targetPath = PATH.resolve(file.getName());
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    }
}
