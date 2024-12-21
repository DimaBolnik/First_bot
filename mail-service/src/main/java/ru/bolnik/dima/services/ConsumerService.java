package ru.bolnik.dima.services;

import ru.bolnik.dima.dto.MailParams;

public interface ConsumerService {
    void consumeRegistrationMail(MailParams mailParams);
}