package br.com.alverad.ecommerce.commons.validation.not_null_when;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ObjectUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IsNullWhenValidator implements ConstraintValidator<IsNullWhen, Object> {

    private String selected;
    private String[] isNull;
    private String message;

    @Override
    public void initialize(IsNullWhen requiredIfChecked) {
        selected = requiredIfChecked.selected();
        isNull = requiredIfChecked.isNull();
        message = requiredIfChecked.message();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            Object checkedValue = BeanUtils.getProperty(object, selected);
            if (checkedValue != null) {
                for (String propName : isNull) {
                    Object requiredValue = BeanUtils.getProperty(object, propName);
                    valid = requiredValue == null && ObjectUtils.isEmpty(requiredValue);
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
