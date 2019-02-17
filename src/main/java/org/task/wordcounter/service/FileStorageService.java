package org.task.wordcounter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.task.wordcounter.StorageProperties;
import org.task.wordcounter.controller.FileUploadController;
import org.task.wordcounter.exception.StorageException;
import org.task.wordcounter.exception.StorageFileNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final Path rootLocation;

    @Autowired
    public FileStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }


    public void store(String name, byte[] content) {
        String fileName = StringUtils.cleanPath(name);
        try (InputStream inputStream = new ByteArrayInputStream(content)) {
            Files.copy(inputStream, this.rootLocation.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Could not store file " + fileName, e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Could not read stored files", e);
        }
    }

    public List<String> generateAllFilesURIs() {
        return loadAll()
                .map(path -> buildURIs(path))
                .collect(Collectors.toList());
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not find file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file for delivery as resource: " + filename, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize file storage", e);
        }
    }

    private String buildURIs(Path path) {
        return MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                .build()
                .toString();
    }

}