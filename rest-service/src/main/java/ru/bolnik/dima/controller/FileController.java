package ru.bolnik.dima.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bolnik.dima.entity.AppDocument;
import ru.bolnik.dima.entity.AppPhoto;
import ru.bolnik.dima.entity.BinaryContent;
import ru.bolnik.dima.service.FileService;

@Log4j
@RequestMapping("/file")
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-doc")
    public ResponseEntity<?> getDoc(@RequestParam("id") String id) {
        //todo для формирования badRequest добавить ControllerAdvice
        AppDocument doc = fileService.getDocument(id);
        if (doc == null) {
            return ResponseEntity.badRequest().build();
        }

        BinaryContent binaryContent = doc.getBinaryContent();
        FileSystemResource fileSystemResource =  fileService.getFileSystemResource(binaryContent);
        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getMimeType()))
                .header("Content-Disposition","attachment; filename" + doc.getDocName())
                .body(fileSystemResource);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-photo")
    public ResponseEntity<?> getPhoto(@RequestParam("id") String id) {
        //todo для формирования badRequest добавить ControllerAdvice
        AppPhoto photo = fileService.getPhoto(id);
        if (photo == null) {
            return ResponseEntity.badRequest().build();
        }

        BinaryContent binaryContent = photo.getBinaryContent();
        FileSystemResource fileSystemResource =  fileService.getFileSystemResource(binaryContent);
        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType((MediaType.IMAGE_JPEG))
                .header("Content-Disposition","attachment;")
                .body(fileSystemResource);
    }
}
