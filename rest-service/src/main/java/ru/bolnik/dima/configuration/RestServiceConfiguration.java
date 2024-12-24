package ru.bolnik.dima.configuration;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestServiceConfiguration {

    @Value("${salt}")
    private String salt;

    @Bean
    public Hashids getHashids() {
        var minHashLength = 10;
        return new Hashids(salt, minHashLength);
    }
}
