package br.com.alverad.ecommerce.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.CurrentSecurityContext;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CurrentSecurityContext(expression = "@jwtTokenToUserDTO.apply(#this.authentication)",
        errorOnInvalidType = true)
public @interface CurrentUser {
}
