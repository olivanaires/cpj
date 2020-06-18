package br.com.ota.cpjbackend.configuration;

import org.springframework.context.MessageSource;

import java.util.Objects;

public class MessageProperty {

    private final MessageSource messageSource;

    public MessageProperty(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, String... args) {
        String message = this.messageSource.getMessage(key, null, null);
        if (args.length > 0 && Objects.nonNull(message)) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{" + i + "}", args[i]);
            }
        }
        return message;
    }

}
