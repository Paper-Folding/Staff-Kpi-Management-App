package ndky.paper.kpimgrapp.Storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorage {
    void deleteOneFile(String filename);

    void createDirectory();

    void storeFile(MultipartFile file, String desiredFileName);

    Resource loadFileAsResource(String filename);
}
