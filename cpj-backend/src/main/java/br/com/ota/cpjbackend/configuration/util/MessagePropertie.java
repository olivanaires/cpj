package br.com.ota.cpjbackend.configuration.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor
public class MessagePropertie {

    private final MessageSource messageSource;

    public String getMessage(String key, String... variables) {
        final Locale locale = new Locale("pt", "BR");
        return messageSource.getMessage(key, getVariableValues(locale, variables), locale);
    }

    public String getMessage(String key, Locale locale, String... variables) {
        return messageSource.getMessage(key, getVariableValues(locale, variables), locale);
    }

    private Object[] getVariableValues(Locale locale, String[] variables) {
        Object[] objects = null;
        if (Objects.nonNull(variables) && variables.length > 0) {

            objects = new Object[variables.length];
            for (int i = 0; i < variables.length; i++) {
                try {
                    objects[i] = messageSource.getMessage(variables[i], null, locale);
                } catch (NoSuchMessageException e) {
                    objects[i] = variables[i];
                }
            }

        }
        return objects;
    }

}
