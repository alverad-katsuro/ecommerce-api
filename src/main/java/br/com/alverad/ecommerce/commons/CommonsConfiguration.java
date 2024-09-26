package br.com.alverad.ecommerce.commons;

import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import br.com.alverad.ecommerce.commons.dto.JwtUserDTO;

@Configuration
public class CommonsConfiguration {

    @Bean
    public Function<JwtAuthenticationToken, JwtUserDTO> jwtTokenToUserDTO() {
        return jwtAuthenticationToken -> {
            if (jwtAuthenticationToken == null) {
                return null;
            } else {
                return new JwtUserDTO(jwtAuthenticationToken);
            }
        };
    }

}
