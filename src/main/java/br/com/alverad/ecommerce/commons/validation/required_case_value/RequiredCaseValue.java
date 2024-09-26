package br.com.alverad.ecommerce.commons.validation.required_case_value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Repeatable(RequiredCaseValues.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RequiredCaseValueValidator.class})
public @interface RequiredCaseValue {

    String message() default "This field is required.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String selected();

    String[] required();

    String[] values();
}
