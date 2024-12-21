package ru.bolnik.dima.services.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.bolnik.dima.dto.MailParams;
import ru.bolnik.dima.services.ConsumerService;
import ru.bolnik.dima.services.MailSenderService;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final MailSenderService mailSenderService;

    public ConsumerServiceImpl(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.registration-mail}")
    public void consumeRegistrationMail(MailParams mailParams) {
        mailSenderService.send(mailParams);
    }
}