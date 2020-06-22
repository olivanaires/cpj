package br.com.ota.cpjbackend.configuration;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageSouceConfiguration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(new Locale("pt", "BR"));
        return messageSource;
    }

    @Bean
    public MessagePropertie messagePropertie() {
        return new MessagePropertie(messageSource());
    }

}
