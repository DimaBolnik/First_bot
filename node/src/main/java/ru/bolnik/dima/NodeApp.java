package ru.bolnik.dima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ru.bolnik.dima.*")
@EntityScan("ru.bolnik.dima.*")
@ComponentScan("ru.bolnik.dima.*")
@SpringBootApplication
public class NodeApp {
    public static void main(String[] args) {
        SpringApplication.run(NodeApp.class);
    }
}
