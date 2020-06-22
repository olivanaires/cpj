package br.com.ota.cpjbackend.configuration;

import br.com.ota.cpjbackend.configuration.util.MessageProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageI18nConfiguration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

    @Bean
    public MessageProperty messageProperty() {
        return new MessageProperty(messageSource());
    }

}
