package br.com.alverad.ecommerce.application.config.security.cookie_auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        if (authentication.getPrincipal() instanceof OidcUserDTO usuario
                && usuario.getAuthorities() != null) {

            Cookie cookie = new Cookie("Token", usuario.getAcessToken().getTokenValue());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            super.onAuthenticationSuccess(request, response, authentication);

        } else {
            response.sendRedirect(request.getContextPath() + "/login?error");
        }

    }

}
