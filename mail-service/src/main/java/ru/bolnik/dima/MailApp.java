package ru.bolnik.dima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ru.bolnik.dima.*")
@SpringBootApplication
public class MailApp {
    public static void main(String[] args) {
        SpringApplication.run(MailApp.class);
    }
}
