package ru.bolnik.dima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bolnik.dima.service.UserActivationService;

@RequestMapping("/user")
@RestController
public class ActivationController {

    private final UserActivationService userActivationService;

    public ActivationController(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activation")
    public ResponseEntity<?> activation(@RequestParam("id") String id) {
        boolean res = userActivationService.activation(id);
        if (res) {
            return ResponseEntity.ok().body("Регистрация успешно завершена!!!");
        }
        return ResponseEntity.badRequest().body("Неверная ссылка!");
    }
}