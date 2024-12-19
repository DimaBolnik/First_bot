package ru.bolnik.dima.services;

import ru.bolnik.dima.dto.MailParams;

public interface MailSenderService {

    void send(MailParams mailParams);
}
