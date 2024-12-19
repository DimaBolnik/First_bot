package ru.bolnik.dima.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bolnik.dima.entity.AppDocument;
import ru.bolnik.dima.entity.AppPhoto;
import ru.bolnik.dima.service.enums.LinkType;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);
    String generateLink(Long docId, LinkType linkType);
}
