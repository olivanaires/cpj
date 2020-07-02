package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalControllerExceptionHandler {

    private final MessagePropertie messagePropertie;

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> constraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest()
                .body(new MessageResponse(messagePropertie.getMessage(ex.getConstraintName())));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MessageResponse> accessDeniedException(ConstraintViolationException ex) {
        System.out.println(ex.getConstraintName());
        return ResponseEntity.badRequest()
                .body(new MessageResponse(messagePropertie.getMessage("message.access.denied")));
    }
}
