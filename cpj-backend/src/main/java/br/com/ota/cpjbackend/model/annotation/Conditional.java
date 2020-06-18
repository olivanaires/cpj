package br.com.ota.cpjbackend.model.annotation;

import br.com.ota.cpjbackend.validator.ConditionalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Repeatable(Conditionals.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConditionalValidator.class})
public @interface Conditional {

    String message() default "This field is required.";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    String selected();

    String[] required();

    String[] values();

}
