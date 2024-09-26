package br.com.alverad.ecommerce.application.config.security.cookie_auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieAuthFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Token")) {
                    token = cookie.getValue();
                }
            }
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        } else {
            try {
                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwtDecoder.decode(token));
                authenticationToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JwtException e) {
                filterChain.doFilter(request, response);
            }

        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Cookie cookie = getCookie(request.getCookies());
        String bearerTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return cookie == null || (bearerTokenHeader != null);
    }

    private Cookie getCookie(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        } else {
            for (Cookie cok : cookies) {
                if (cok.getName().equals("Token")) {
                    return cok;
                }
            }
        }
        return null;
    }

}
