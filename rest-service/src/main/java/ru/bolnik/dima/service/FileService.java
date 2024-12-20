package ru.bolnik.dima.service;

import ru.bolnik.dima.entity.AppDocument;
import ru.bolnik.dima.entity.AppPhoto;

public interface FileService {

    AppDocument getDocument(String id);

    AppPhoto getPhoto(String id);
}
