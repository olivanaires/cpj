package br.com.ota.cpjbackend.configuration.util;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;
import java.util.Objects;

public class MessageProperty {

    private final MessageSource messageSource;

    public MessageProperty(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, String... args) {
        String message = this.messageSource.getMessage(key, null, new Locale("pt"));
        if (args.length > 0 && Objects.nonNull(message)) {
            for (int i = 0; i < args.length; i++) {

                String arg;
                try {
                    arg = this.messageSource.getMessage(args[i], null, new Locale("pt"));
                } catch (NoSuchMessageException ex) {
                    arg = args[i];
                }
                message = message.replace("{" + i + "}", args[i]);
            }
        }
        return message;
    }

}
