package com.kuropatin.zenbooking.security.util;

import com.kuropatin.zenbooking.security.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthenticationUtils {

    public long getId() {
        return getSecurityUser().getId();
    }

    public String getUsername() {
        return getSecurityUser().getUsername();
    }

    public String getPassword() {
        return getSecurityUser().getPassword();
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return getSecurityUser().getAuthorities();
    }

    private SecurityUser getSecurityUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) auth.getPrincipal();
    }
}