package br.com.alverad.ecommerce.commons.validation.not_null_when;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface IsNullWhens {
    IsNullWhen[] value();
}
