package ndky.paper.kpimgrapp.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class CertificateStorageImpl implements IStorage {
    private final Path certPath;

    @Autowired
    public CertificateStorageImpl(StorageProperties properties) {
        this.certPath = Paths.get(properties.getCert());
    }

    @Override
    public void deleteOneFile(String filename) {
        try {
            Files.deleteIfExists(certPath.resolve(filename));
        } catch (IOException e) {
            throw new StorageException("Failed to delete " + filename);
        }
    }

    @Override
    public void createDirectory() {
        try {
            if (Files.notExists(certPath))
                Files.createDirectory(certPath);
        } catch (IOException e) {
            throw new StorageException("Failed to create directory: " + certPath);
        }
    }

    @Override
    public void storeFile(MultipartFile file, String desiredFileName) {
        if (file.isEmpty()) {
            throw new StorageException("Cannot store empty file");
        }
        try {
            Path destination = certPath.resolve(desiredFileName);
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
                return null;
            Path cert = certPath.resolve(filename);
            if (Files.exists(cert) && Files.isReadable(cert)) {
                return new UrlResource(cert.toUri());
            }
        } catch (MalformedURLException ignored) {
        }
        return null;
    }
}
