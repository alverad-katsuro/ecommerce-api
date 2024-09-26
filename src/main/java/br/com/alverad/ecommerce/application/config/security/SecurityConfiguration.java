package br.com.alverad.ecommerce.application.config.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import br.com.alverad.ecommerce.application.config.properties.AuthProperties;
import br.com.alverad.ecommerce.application.config.properties.CorsProperties;
import br.com.alverad.ecommerce.application.config.security.cookie_auth.CookieAuthFilter;
import br.com.alverad.ecommerce.application.config.security.cookie_auth.CustomOidcUserService;
import br.com.alverad.ecommerce.application.config.security.cookie_auth.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CorsProperties corsProperties;

    private final AuthProperties authProperties;

    private final CookieAuthFilter cookieAuthFilter;

    private final LoginSuccessHandler loginSuccessHandler;

    private final CustomOidcUserService customOidcUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .anonymous(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(e -> e.successHandler(loginSuccessHandler)
                        .userInfoEndpoint(user -> user.oidcUserService(customOidcUserService)))
                .formLogin(e -> e.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                                "/websocket*/**", "/login**", "/")
                        .permitAll().requestMatchers("/actuator/**").hasRole("SPRING_ACTUATOR")
                        .anyRequest().authenticated())

                .addFilterBefore(cookieAuthFilter, BearerTokenAuthenticationFilter.class)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of(corsProperties.getAllowedOriginPatterns()));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type",
                "Content-Type", "Access-Control-Allow-Headers", "Access-Control-Allow-Origin"));
        configuration
                .setExposedHeaders(Arrays.asList("X-Get-Header", "Access-Control-Allow-Methods"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> resourceAcess = jwt.getClaimAsMap("resource_access");
            if (resourceAcess != null
                    && resourceAcess.containsKey(authProperties.clientDefault())) {
                Object roles =
                        ((Map<String, Object>) resourceAcess.get(authProperties.clientDefault()))
                                .get("roles");
                if (roles instanceof List<?>) {
                    List<String> rolesList = (List<String>) roles;
                    JwtGrantedAuthoritiesConverter scopesConverter =
                            new JwtGrantedAuthoritiesConverter();
                    Collection<GrantedAuthority> allAuthorities = scopesConverter.convert(jwt);
                    allAuthorities.addAll(rolesList.stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList());
                    return allAuthorities;
                } else {
                    return Collections.emptyList();
                }
            } else {
                return Collections.emptyList();
            }
        });

        return jwtAuthenticationConverter;
    }
}
