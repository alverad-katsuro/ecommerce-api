package br.com.alverad.ecommerce.application.config.security.cookie_auth;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class OidcUserDTO implements OidcUser {

    private OidcIdToken oidcIdToken;

    private Map<String, Object> claims;

    private OAuth2AccessToken acessToken;

    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Map<String, Object> getClaims() {
        return this.claims;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.oidcIdToken;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    public OidcIdToken getOidcIdToken() {
        return oidcIdToken;
    }

    public void setOidcIdToken(OidcIdToken oidcIdToken) {
        this.oidcIdToken = oidcIdToken;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.claims;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return (String) this.claims.get("name");
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public OAuth2AccessToken getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(OAuth2AccessToken acessToken) {
        this.acessToken = acessToken;
    }

}
