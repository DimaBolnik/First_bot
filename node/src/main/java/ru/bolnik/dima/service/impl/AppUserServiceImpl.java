package ru.bolnik.dima.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.bolnik.dima.dao.AppUserDAO;
import ru.bolnik.dima.dto.MailParams;
import ru.bolnik.dima.entity.AppUser;
import ru.bolnik.dima.service.AppUserService;
import ru.bolnik.dima.utils.CryptoTool;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static ru.bolnik.dima.entity.enums.UserState.BASIC_STATE;
import static ru.bolnik.dima.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;

@Log4j
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;

    @Value("${service.mail.uri}")
    private String mailServiceUri;

    public AppUserServiceImpl(AppUserDAO appUserDAO, CryptoTool cryptoTool) {
        this.appUserDAO = appUserDAO;
        this.cryptoTool = cryptoTool;
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
        var appUserOpt = appUserDAO.findByEmail(email);
        if (appUserOpt.isEmpty()) {
            appUser.setEmail(email);
            appUser.setState(BASIC_STATE);
            appUser = appUserDAO.save(appUser);

            var cryptoUserId = cryptoTool.hashOf(appUser.getId());
            ResponseEntity<String> response = sendRegistrationMail(cryptoUserId, email);
            if (response.getStatusCode() != HttpStatus.OK) {
                String msg = String.format("Отправка эл. письма на почту %s не удалась.", email);
                log.error(msg);
                appUser.setEmail(null);
                appUserDAO.save(appUser);
                return msg;
            }
            return "Вам на почту было отправлено письмо. "
                   + "Перейдите по ссылке в письме для подтверждения регистрации.";
        } else {
            return "Этот email уже используется. Введите корректный email."
                   + " Для отмены команды введите /cancel";
        }
    }

    private ResponseEntity<String> sendRegistrationMail(String cryptoUserId, String email) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .emailTo(email)
                .build();
        var request = new HttpEntity<>(mailParams, headers);

        ResponseEntity<String> exchange = null;
        try {
            exchange = restTemplate.exchange(mailServiceUri, HttpMethod.POST,
                    request, String.class);

            if (!exchange.getStatusCode().is2xxSuccessful()) {
                log.error("Сервер вернул ошибочный статус: {}");
                // Здесь можно добавить дополнительную логику обработки ошибочных статусов
            }
        } catch (ResourceAccessException e) {
            log.error("Проблема с сетевым подключением", e);
        } catch (HttpStatusCodeException e) {
            log.error("HTTP ошибка: {}");
        } catch (RestClientException e) {
            log.error("Общая ошибка REST-клиента", e);
        }
        return exchange;
    }
}