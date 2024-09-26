package br.com.alverad.ecommerce.commons.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConditionalNotNullValidator
        implements ConstraintValidator<ConditionalNotNull, Object> {

    private String field;

    @Override
    public void initialize(ConditionalNotNull constraintAnnotation) {
        this.field = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are accepted, let @NotNull handle it
        }

        try {
            // Use reflection to get the value of the condition field
            Object conditionFieldValue =
                    value.getClass().getDeclaredMethod("get" + capitalize(field)).invoke(value);

            // Check the condition and validate accordingly
            return conditionFieldValue != null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error accessing condition field", e);
        }
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
