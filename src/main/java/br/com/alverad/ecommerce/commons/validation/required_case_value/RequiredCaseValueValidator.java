package br.com.alverad.ecommerce.commons.validation.required_case_value;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ObjectUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequiredCaseValueValidator implements ConstraintValidator<RequiredCaseValue, Object> {

    private String selected;
    private String[] required;
    private String message;
    private String[] values;

    @Override
    public void initialize(RequiredCaseValue requiredIfChecked) {
        selected = requiredIfChecked.selected();
        required = requiredIfChecked.required();
        message = requiredIfChecked.message();
        values = requiredIfChecked.values();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            Object checkedValue = BeanUtils.getProperty(object, selected);

            if (Arrays.asList(values).contains(checkedValue)
                    || (Arrays.asList(values).contains("null") && checkedValue == null)) {
                for (String propName : required) {
                    Object requiredValue = BeanUtils.getProperty(object, propName);
                    valid = requiredValue != null && !ObjectUtils.isEmpty(requiredValue);
                    if (!valid) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(
                                message.replace("{}", selected)).addPropertyNode(propName)
                                .addConstraintViolation();
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Accessor method is not available for class : {}, exception : {}",
                    object.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            log.error("Field or method is not present on class : {}, exception : {}",
                    object.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            log.error("An exception occurred while accessing class : {}, exception : {}",
                    object.getClass().getName(), e);
            e.printStackTrace();
            return false;
        }
        return valid;
    }
}
