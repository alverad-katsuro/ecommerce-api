package br.com.alverad.ecommerce.commons.dto;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserDTO {

    private String id;

    private String username;

    private String nome;

    private String email;

    private Set<GrantedAuthority> roles;

    private String token;

    public JwtUserDTO(JwtAuthenticationToken jwtAuthenticationToken) {
        this.id = jwtAuthenticationToken.getTokenAttributes().get("sub").toString();
        this.email = jwtAuthenticationToken.getTokenAttributes().get("email").toString();
        this.nome = jwtAuthenticationToken.getTokenAttributes().get("name").toString();
        this.username =
                jwtAuthenticationToken.getTokenAttributes().get("preferred_username").toString();
        this.roles = jwtAuthenticationToken.getAuthorities().stream().collect(Collectors.toSet());
        this.token = jwtAuthenticationToken.getToken().getTokenValue();
    }
}
