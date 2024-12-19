package ru.bolnik.dima.service;

import org.springframework.core.io.FileSystemResource;
import ru.bolnik.dima.entity.AppDocument;
import ru.bolnik.dima.entity.AppPhoto;
import ru.bolnik.dima.entity.BinaryContent;

public interface FileService {
    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
