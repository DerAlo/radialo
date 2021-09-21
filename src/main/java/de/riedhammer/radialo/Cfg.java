package de.riedhammer.radialo;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Cfg {
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
