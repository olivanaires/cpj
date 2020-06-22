package br.com.ota.cpjbackend.configuration.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public class MessagePropertie {

    private final MessageSource messageSource;

    public String getMessage(String key, String... variables) {
        return messageSource.getMessage(key, variables, new Locale("pt", "BR"));
    }

    public String getMessage(String key, Locale locale, String... variables) {
        return messageSource.getMessage(key, variables, locale);
    }


}
