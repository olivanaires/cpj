package br.com.ota.cpjbackend.validator;

import br.com.ota.cpjbackend.configuration.MessageProperty;
import br.com.ota.cpjbackend.model.annotation.Conditional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static org.springframework.util.StringUtils.isEmpty;

@RequiredArgsConstructor
public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

    private final MessageProperty messageProperty;

    private String selected;
    private String[] required;
    private String[] values;

    @Override
    public void initialize(Conditional requiredIfChecked) {
        selected = requiredIfChecked.selected();
        required = requiredIfChecked.required();
        values = requiredIfChecked.values();
    }

    @Override
    public boolean isValid(Object objectToValidate, ConstraintValidatorContext context) {
        boolean valid = true;
        BeanWrapperImpl wrapper = new BeanWrapperImpl(objectToValidate);
        Object actualValue = wrapper.getPropertyValue(selected);
        if (Arrays.asList(values).contains(actualValue.toString())) {

            for (String propName : required) {
                Object requiredValue = wrapper.getPropertyValue(propName);
                valid = requiredValue != null && !isEmpty(requiredValue);

                if (!valid) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(messageProperty.getMessage("field.required", propName))
                            .addPropertyNode(propName).addConstraintViolation();
                }
            }
        }
        return valid;
    }

}
