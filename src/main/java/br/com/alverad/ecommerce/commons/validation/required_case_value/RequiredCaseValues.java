package br.com.alverad.ecommerce.commons.validation.required_case_value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RequiredCaseValues {
    RequiredCaseValue[] value();
}
