package ru.bolnik.dima.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.bolnik.dima.dao.AppDocumentDAO;
import ru.bolnik.dima.dao.AppPhotoDAO;
import ru.bolnik.dima.entity.AppDocument;
import ru.bolnik.dima.entity.AppPhoto;
import ru.bolnik.dima.service.FileService;
import ru.bolnik.dima.utils.Decoder;

@Log4j
@Service
public class FileServiceImpl implements FileService {

    private final AppDocumentDAO appDocumentDAO;
    private final AppPhotoDAO appPhotoDAO;
    private final Decoder decoder;

    public FileServiceImpl(AppDocumentDAO appDocumentDAO, AppPhotoDAO appPhotoDAO, Decoder decoder) {
        this.appDocumentDAO = appDocumentDAO;
        this.appPhotoDAO = appPhotoDAO;
        this.decoder = decoder;
    }

    @Override
    public AppDocument getDocument(String hash) {
        Long id = decoder.idOf(hash);
        if (id == null) {
            return null;
        }
        return appDocumentDAO.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String hash) {
        Long id = decoder.idOf(hash);
        if (id == null) {
            return null;
        }
        return appPhotoDAO.findById(id).orElse(null);
    }
}
