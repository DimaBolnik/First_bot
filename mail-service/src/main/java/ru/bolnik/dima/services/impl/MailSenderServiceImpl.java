package ru.bolnik.dima.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.bolnik.dima.dto.MailParams;
import ru.bolnik.dima.services.MailSenderService;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${service.activation.uri}")
    private String activationServiceUri;

    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(MailParams mailParams) {
        String subject = "Активация учетной записи";
        String messageBody = getActivationMailBody(mailParams.getId());
        String emailTo = mailParams.getEmailTo();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        mailSender.send(mailMessage);

    }

    private String getActivationMailBody(String id) {
        String msg = String.format("Для завершения активации перейдите по ссылке:\n%s",
                activationServiceUri);
        return msg.replace("{id}", id);
    }
}
