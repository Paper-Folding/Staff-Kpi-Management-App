package ndky.paper.kpimgrapp.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AvatarStorageImpl implements IStorage {
    private final Path avatarPath;

    @Autowired
    public AvatarStorageImpl(StorageProperties properties) {
        this.avatarPath = Paths.get(properties.getAvatar());
    }

    @Override
    public void deleteOneFile(String filename) {
        try {
            Files.deleteIfExists(avatarPath.resolve(filename));
        } catch (IOException e) {
            throw new StorageException("Failed to delete " + filename);
        }
    }

    @Override
    public void createDirectory() {
        try {
            if (Files.notExists(avatarPath))
                Files.createDirectory(avatarPath);
        } catch (IOException e) {
            throw new StorageException("Failed to create directory: " + avatarPath);
        }
    }

    @Override
    public void storeFile(MultipartFile file, String desiredFileName) {
        if (file.isEmpty()) {
            throw new StorageException("Cannot store empty file");
        }
        try {
            Path destination = avatarPath.resolve(desiredFileName);
            InputStream in = file.getInputStream();
            Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + desiredFileName);
        }
    }

    @Override
    public Resource loadFileAsResource(String filename) {
        try {
            if (filename == null || "".equals(filename))
                return loadFileAsDefaultResource();
            Path avatar = avatarPath.resolve(filename);
            if (Files.exists(avatar) && Files.isReadable(avatar)) {
                return new UrlResource(avatar.toUri());
            }
        } catch (MalformedURLException ignored) {
        }
        return loadFileAsDefaultResource();
    }

    public Resource loadFileAsDefaultResource() {
        try {
            Path fallback = ResourceUtils.getFile("classpath:static/images/default-avatar.jpg").toPath();
            if (Files.exists(fallback) && Files.isReadable(fallback)) {
                return new UrlResource(fallback.toUri());
            }
        } catch (FileNotFoundException e) {
            throw new StorageException("Default avatar is not found");
        } catch (MalformedURLException ignored) {
        }
        return null;
    }
}
