package ru.bolnik.dima.service.impl;

import lombok.extern.log4j.Log4j;
import org.hashids.Hashids;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.bolnik.dima.dao.AppUserDAO;
import ru.bolnik.dima.dto.MailParams;
import ru.bolnik.dima.entity.AppUser;
import ru.bolnik.dima.service.AppUserService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import java.util.Optional;

import static ru.bolnik.dima.enums.UserState.BASIC_STATE;
import static ru.bolnik.dima.enums.UserState.WAIT_FOR_EMAIL_STATE;

@Log4j
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDAO appUserDAO;
    private final Hashids hashids;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queues.registration-mail}")
    private String registrationMailQueue;

    public AppUserServiceImpl(AppUserDAO appUserDAO, Hashids hashids, RabbitTemplate rabbitTemplate) {
        this.appUserDAO = appUserDAO;
        this.hashids = hashids;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public String registerUser(AppUser appUser) {
        if (appUser.getIsActive()) {
            return "Вы уже зарегистрированы!";
        } else if (appUser.getEmail() != null) {
            return "Вам на почту уже было отправлено письмо. "
                   + "Перейдите по ссылке в письме для подтверждения регистрации.";
        }
        appUser.setState(WAIT_FOR_EMAIL_STATE);
        appUserDAO.save(appUser);
        return "Введите, пожалуйста, ваш email:";
    }

    @Override
    public String setEmail(AppUser appUser, String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            return "Введите, пожалуйста, корректный email. Для отмены команды введите /cancel";
        }

        Optional<AppUser> appUserOpt = appUserDAO.findByEmail(email);

        if (appUserOpt.isEmpty()) {
            appUser.setEmail(email);
            appUser.setState(BASIC_STATE);
            appUser = appUserDAO.save(appUser);

            String cryptoUserId = hashids.encode(appUser.getId());
            sendRegistrationMail(cryptoUserId, email);
            return "Вам на почту было отправлено письмо."
                   + "Перейдите по ссылке в письме для подтверждения регистрации.";
        } else {
            return "Этот email уже используется. Введите корректный email."
                   + " Для отмены команды введите /cancel";
        }
    }

    private void sendRegistrationMail(String cryptoUserId, String email) {
        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .emailTo(email)
                .build();
        rabbitTemplate.convertAndSend(registrationMailQueue, mailParams);
    }
}